package org.example.baitap.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Order {
    private int id;
    private int userId;
    private double totals;
    private Status status = Status.WAITING;
    public enum Status {
        WAITING,
        CONFIRM,
        DELIVERY,
        SUCCESS,
        CANCEL
    }
}
