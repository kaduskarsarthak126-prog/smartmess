package com.smartmess.smartmess.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.smartmess.smartmess.dto.DishResponse;
import com.smartmess.smartmess.dto.MenuRequest;
import com.smartmess.smartmess.dto.MenuResponse;
import com.smartmess.smartmess.model.Dish;
import com.smartmess.smartmess.model.Menu;
import com.smartmess.smartmess.model.MenuDish;
import com.smartmess.smartmess.repository.DishRepository;
import com.smartmess.smartmess.repository.MenuDishRepository;
import com.smartmess.smartmess.repository.MenuRepository;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuDishRepository menuDishRepository;
    private final DishRepository dishRepository;

    public MenuService(MenuRepository menuRepository,
                       MenuDishRepository menuDishRepository,
                       DishRepository dishRepository) {
        this.menuRepository = menuRepository;
        this.menuDishRepository = menuDishRepository;
        this.dishRepository = dishRepository;
    }

    // @Transactional: menu aur menu_dishes dono save hon ya dono nahi.
    // Beech mein error aaya to sab wapas (rollback), adhoora data nahi bachega.
    @Transactional
    public MenuResponse createMenu(MenuRequest request) {
        // Rule 1: zaroori fields
        if (request.menuDate() == null || request.mealType() == null || request.choiceDeadline() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "menuDate, mealType and choiceDeadline are required");
        }
        if (request.dishIds() == null || request.dishIds().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Select at least one dish");
        }

        // Rule 2: deadline us din ke khane se pehle ya usi din honi chahiye
        if (request.choiceDeadline().toLocalDate().isAfter(request.menuDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Deadline cannot be after the menu date");
        }

        // Rule 3: same date + same meal ka menu dobara nahi
        if (menuRepository.existsByMenuDateAndMealType(request.menuDate(), request.mealType())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Menu already exists for " + request.menuDate() + " " + request.mealType());
        }

        // Rule 4: saari dish id database mein honi chahiye (duplicate id hata dete hain)
        List<Long> ids = request.dishIds().stream().distinct().toList();
        List<Dish> dishes = dishRepository.findAllById(ids);
        if (dishes.size() != ids.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more dish ids do not exist");
        }

        Menu menu = new Menu();
        menu.setMenuDate(request.menuDate());
        menu.setMealType(request.mealType());
        menu.setChoiceDeadline(request.choiceDeadline());
        Menu savedMenu = menuRepository.save(menu);

        // Har dish ke liye ek MenuDish row
        for (Dish dish : dishes) {
            MenuDish md = new MenuDish();
            md.setMenu(savedMenu);
            md.setDish(dish);
            menuDishRepository.save(md);
        }

        return toResponse(savedMenu, dishes);
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> getMenusByDate(LocalDate date) {
        return menuRepository.findByMenuDate(date).stream()
                .map(menu -> {
                    List<Dish> dishes = menuDishRepository.findByMenuId(menu.getId()).stream()
                            .map(MenuDish::getDish)
                            .toList();
                    return toResponse(menu, dishes);
                })
                .toList();
    }

    // Menu + dishes ko MenuResponse mein badalne ka helper
    private MenuResponse toResponse(Menu menu, List<Dish> dishes) {
        List<DishResponse> dishResponses = dishes.stream().map(DishResponse::from).toList();
        return new MenuResponse(menu.getId(), menu.getMenuDate(), menu.getMealType(),
                menu.getChoiceDeadline(), dishResponses);
    }
}