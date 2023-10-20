package com.larex.SmartNote.entity.wrapper;

import lombok.Data;

@Data
public class NoteWrapper {

    private String title;
    private String body;
    private Boolean publicRead = false;
    private Boolean publicWrite = false;

}
