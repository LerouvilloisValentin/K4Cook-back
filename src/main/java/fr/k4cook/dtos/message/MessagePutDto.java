package fr.k4cook.dtos.message;

import jakarta.validation.constraints.NotNull;

/**
 * Message Post Dto expected from front
 */
public class MessagePutDto {

    /**
     * Message's content
     */
    @NotNull
    private String content;

    /**
     * Empty constructor
     */
    public MessagePutDto() {
        super();
    }

    /**
     * Full constructor
     * 
     * @param content String
     */
    public MessagePutDto(String content) {
        super();
        this.content = content;
    }

    /**
     * Get content
     * 
     * @return String
     */
    public String getContent() {
        return content;
    }

    /**
     * Set content
     * 
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}
