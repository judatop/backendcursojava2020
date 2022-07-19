package net.copicomp.backendcursojava.models.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class PostCreateRequestModel {

    @NotEmpty( message =  "El titulo es obligatorio")
    private String title;

    @NotEmpty( message =  "El contenido es obligatorio")
    private String content;

    @NotNull( message = "La exposición del post es obligatoria")
    @Range(min=1, max=2, message = "La exposición del post es inválida")
    private long exposureId;

    @NotNull( message = "El tiempo de expiración es obligatorio")
    @Range(min=0, max=1440, message = "El tiempo de expiración es inválido")
    private int expirationTime;


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getExposureId() {
        return this.exposureId;
    }

    public void setExposureId(long exposureId) {
        this.exposureId = exposureId;
    }

    public int getExpirationTime() {
        return this.expirationTime;
    }

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    
}
