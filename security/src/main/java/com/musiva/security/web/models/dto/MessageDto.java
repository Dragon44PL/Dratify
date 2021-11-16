package com.musiva.security.web.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MessageDto {

    private String name;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private String creationDate;

    public MessageDto(String name, String creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public MessageDto(String name) {
        this.name = name;
    }

    private MessageDto() {
        this.name = "";
        this.creationDate = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationDate() {
        return creationDate;
    }
}
