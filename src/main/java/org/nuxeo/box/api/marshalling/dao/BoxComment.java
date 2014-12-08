package org.nuxeo.box.api.marshalling.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Map;

/**
 * Comment.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", defaultImpl = BoxComment.class)
public class BoxComment extends BoxTypedObject {

    public static final String FIELD_IS_REPLY_COMMENT = "is_reply_comment";

    public static final String FIELD_MESSAGE = "message";

    public static final String FIELD_CREATED_BY = "created_by";

    public static final String FIELD_ITEM = "item";

    /**
     * Constructor.
     */
    public BoxComment() {
        setType(BoxResourceType.COMMENT.toString());
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxComment(BoxComment obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a field.
     *
     * @param map
     */
    public BoxComment(Map<String, Object> map) {
        super(map);
    }

    /**
     * Whether this is a comment replying another comment.
     *
     * @return Whether this is a comment replying another comment.
     */
    @JsonProperty(FIELD_IS_REPLY_COMMENT)
    public Boolean isReplyComment() {
        return (Boolean) getValue(FIELD_IS_REPLY_COMMENT);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param isReplyComment whether it's reply comment
     */
    @JsonProperty(FIELD_IS_REPLY_COMMENT)
    private void setIsReplyComment(Boolean isReplyComment) {
        put(FIELD_IS_REPLY_COMMENT, isReplyComment);
    }

    /**
     * Get the comment String.
     *
     * @return The comment String.
     */
    @JsonProperty(FIELD_MESSAGE)
    public String getMessage() {
        return (String) getValue(FIELD_MESSAGE);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param message the comment String.
     */
    @JsonProperty(FIELD_MESSAGE)
    private void setMessage(String message) {
        put(FIELD_MESSAGE, message);
    }

    /**
     * Get the user creating this comment.
     *
     * @return the user creating this comment
     */
    @JsonProperty(FIELD_CREATED_BY)
    public BoxUser getCreatedBy() {
        return (BoxUser) getValue(FIELD_CREATED_BY);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param createdBy user creating this comment
     */
    @JsonProperty(FIELD_CREATED_BY)
    private void setCreatedBy(BoxUser createdBy) {
        put(FIELD_CREATED_BY, createdBy);
    }

    /**
     * Get the object being commented.
     *
     * @return the object being commented
     */
    @JsonProperty(FIELD_ITEM)
    public BoxTypedObject getItem() {
        return (BoxTypedObject) getValue(FIELD_ITEM);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus .org">Jackson JSON processer</a>}
     *
     * @param item the object commented
     */
    @JsonProperty(FIELD_ITEM)
    private void setItem(BoxTypedObject item) {
        put(FIELD_ITEM, item);
    }
}
