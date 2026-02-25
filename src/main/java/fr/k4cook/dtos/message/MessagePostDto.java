package fr.k4cook.dtos.message;

import jakarta.validation.constraints.NotNull;

/**
 * Message Post Dto expected from front
 */
public class MessagePostDto {

    /**
     * Section id
     */
    @NotNull
    private int sectionId;

    /**
     * Message's content
     */
    @NotNull
    private String content;

    /**
     * Parent Message's id if present
     */
    private int parentMessageId = -1;

    /**
     * Empty constructor
     */
    public MessagePostDto() {
        super();
    }

    /**
     * Full constructor
     * 
     * @param sectionId int
     * @param content   String
     */
    public MessagePostDto(int sectionId, String content, int parentMessageId) {
        super();
        this.sectionId = sectionId;
        this.content = content;
        this.parentMessageId = parentMessageId;
    }

    /**
     * Get section's id
     * 
     * @return int
     */
    public int getSectionId() {
        return sectionId;
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
     * Get Parent Message Id
     * 
     * @return int
     */
    public int getParentMessageId() {
        return parentMessageId;
    }

    /**
     * Set Section's id
     * 
     * @param sectionId
     */
    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * Set content
     * 
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Set parent message id
     * 
     * @param parentMessageId
     */
    public void setParentMessageId(int parentMessageId) {
        this.parentMessageId = parentMessageId;
    }
}
