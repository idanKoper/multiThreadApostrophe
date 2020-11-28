package com.zoomInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Document {
    private long id;
    private String firstName;
    private String lastName;
    private double age;
}
