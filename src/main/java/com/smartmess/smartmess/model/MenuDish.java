package com.smartmess.smartmess.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "menu_dishes",
       uniqueConstraints = @UniqueConstraint(columnNames = {"menu_id", "dish_id"}))
public class MenuDish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne: bahut MenuDish rows ek hi Menu se jud sakti hain.
    // LAZY: Menu tabhi load hoga jab zaroorat ho (fast rehta hai).
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    // Manager ne kitna banane ka plan kiya (kg). Optional, baad mein demand se compare hoga.
    @Column(name = "planned_quantity")
    private Double plannedQuantity;

    public MenuDish() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Menu getMenu() { return menu; }
    public void setMenu(Menu menu) { this.menu = menu; }

    public Dish getDish() { return dish; }
    public void setDish(Dish dish) { this.dish = dish; }

    public Double getPlannedQuantity() { return plannedQuantity; }
    public void setPlannedQuantity(Double plannedQuantity) { this.plannedQuantity = plannedQuantity; }
}