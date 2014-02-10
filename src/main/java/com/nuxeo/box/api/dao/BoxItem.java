package com.nuxeo.box.api.dao;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Map;

/**
 * Box item, this is a base class for the box items({@link com.nuxeo.box.api
 * .dao.BoxFile}/{@link
 * BoxFolder}/...)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,
        property = "type", defaultImpl = BoxItem.class)
public class BoxItem extends BoxTypedObject {

    public static final String FIELD_ETAG = "etag";

    public static final String FIELD_SEQUENCE_ID = "sequence_id";

    public static final String FIELD_NAME = "name";

    public static final String FIELD_DESCRIPTION = "description";

    public static final String FIELD_SIZE = "size";

    public static final String FIELD_ITEM_STATUS = "item_status";

    public static final String FIELD_SHARED_LINK = "shared_link";

    public static final String FIELD_CREATED_BY = "created_by";

    public static final String FIELD_MODIFIED_BY = "modified_by";

    public static final String FIELD_OWNED_BY = "owned_by";

    public static final String FIELD_PARENT = "parent";

    public static final String FIELD_PATH_COLLECTION = "path_collection";

    public static final String FIELD_TAGS = "tags";

    public BoxItem() {
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxItem(BoxItem obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a
     * field.
     *
     * @param map
     */
    public BoxItem(Map<String, Object> map) {
        super(map);
    }

    /**
     * Get the path of folders to this item, starting at the root.
     *
     * @return the path_collection
     */
    @JsonProperty(FIELD_PATH_COLLECTION)
    public BoxCollection getPathCollection() {
        return (BoxCollection) getValue(FIELD_PATH_COLLECTION);
    }

    /**
     * This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param pathCollection the path_collection to set
     */
    @JsonProperty(FIELD_PATH_COLLECTION)
    private void setPathCollection(BoxCollection pathCollection) {
        put(FIELD_PATH_COLLECTION, pathCollection);
    }

    /**
     * Get the tags that are set on this item.
     *
     * @return the tags
     */
    @JsonProperty(FIELD_TAGS)
    public String[] getTags() {
        return (String[]) getValue(FIELD_TAGS);
    }

    /**
     * This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param tags the tags to set
     */
    @JsonProperty(FIELD_TAGS)
    private void setTags(String[] tags) {
        put(FIELD_TAGS, tags);
    }

    /**
     * Getter.
     *
     * @return sequence id
     */
    // NXIO-45: don't skip null value to fit to Box json payload response
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonProperty(FIELD_SEQUENCE_ID)
    public String getSequenceId() {
        return (String) getValue(FIELD_SEQUENCE_ID);
    }

    /**
     * Setter.This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param sequenceId sequence id
     */
    @JsonProperty(FIELD_SEQUENCE_ID)
    private void setSequenceId(String sequenceId) {
        put(FIELD_SEQUENCE_ID, sequenceId);
    }

    /**
     * Get name of the item.
     *
     * @return name of the item
     */
    @JsonProperty(FIELD_NAME)
    public String getName() {
        return (String) getValue(FIELD_NAME);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param name name of the item
     */
    @JsonProperty(FIELD_NAME)
    private void setName(String name) {
        put(FIELD_NAME, name);
    }

    /**
     * Get description of the item.
     *
     * @return description of the item
     */
    @JsonProperty(FIELD_DESCRIPTION)
    public String getDescription() {
        return (String) getValue(FIELD_DESCRIPTION);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param description description
     */
    @JsonProperty(FIELD_DESCRIPTION)
    private void setDescription(String description) {
        put(FIELD_DESCRIPTION, description);
    }

    /**
     * Get size of the box item. In bytes.
     *
     * @return size of the box item in bytes.
     */
    @JsonProperty(FIELD_SIZE)
    public Double getSize() {
        return (Double) getValue(FIELD_SIZE);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param size
     */
    @JsonProperty(FIELD_SIZE)
    private void setSize(Double size) {
        put(FIELD_SIZE, size);
    }

    /**
     * Get shared link of box item. Note if there is not shared link created,
     * this is null.
     *
     * @return shared link of box item
     */
    @JsonProperty(FIELD_SHARED_LINK)
    public BoxSharedLink getSharedLink() {
        return (BoxSharedLink) getValue(FIELD_SHARED_LINK);
    }

    /**
     * Set the shared link.
     *
     * @param sharedLink shared link
     */
    @JsonProperty(FIELD_SHARED_LINK)
    private void setSharedLink(BoxSharedLink sharedLink) {
        put(FIELD_SHARED_LINK, sharedLink);
    }

    /**
     * Get the user creating this item.
     *
     * @return user creating this item
     */
    @JsonProperty(FIELD_CREATED_BY)
    public BoxUser getCreatedBy() {
        return (BoxUser) getValue(FIELD_CREATED_BY);
    }

    /**
     * Set the user creating this item.
     *
     * @param createdBy created by
     */
    @JsonProperty(FIELD_CREATED_BY)
    private void setCreatedBy(BoxUser createdBy) {
        put(FIELD_CREATED_BY, createdBy);
    }

    /**
     * Get the user last modified the item.
     *
     * @return user last modified the item
     */
    @JsonProperty(FIELD_MODIFIED_BY)
    public BoxUser getModifiedBy() {
        return (BoxUser) getValue(FIELD_MODIFIED_BY);
    }

    /**
     * Set the user last modified the item.
     *
     * @param modifiedBy
     */
    @JsonProperty(FIELD_MODIFIED_BY)
    private void setModifiedBy(BoxUser modifiedBy) {
        put(FIELD_MODIFIED_BY, modifiedBy);
    }

    /**
     * Get owner of the item.
     *
     * @return owner of the item
     */
    @JsonProperty(FIELD_OWNED_BY)
    public BoxUser getOwnedBy() {
        return (BoxUser) getValue(FIELD_OWNED_BY);
    }

    /**
     * Set the owner.
     *
     * @param ownedBy owner
     */
    @JsonProperty(FIELD_OWNED_BY)
    private void setOwnedBy(BoxUser ownedBy) {
        put(FIELD_OWNED_BY, ownedBy);
    }

    /**
     * Get parent folder.
     *
     * @return parent folder
     */
    @JsonProperty(FIELD_PARENT)
    public BoxFolder getParent() {
        return (BoxFolder) getValue(FIELD_PARENT);
    }

    /**
     * Set the parent.
     *
     * @param parent parent folder
     */
    @JsonProperty(FIELD_PARENT)
    private void setParent(BoxFolder parent) {
        put(FIELD_PARENT, parent);
    }

    /**
     * Get etag.
     *
     * @return etag
     */
    // NXIO-45: don't skip null value to fit to Box json payload response
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonProperty(FIELD_ETAG)
    public String getEtag() {
        return (String) getValue(FIELD_ETAG);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param etag etag
     */
    @JsonProperty(FIELD_ETAG)
    private void setEtag(String etag) {
        put(FIELD_ETAG, etag);
    }

    /**
     * Get the status of this item, which indicates whether this item is
     * deleted or not.
     *
     * @return the item_status
     */
    @JsonProperty(FIELD_ITEM_STATUS)
    public String getItemStatus() {
        return (String) getValue(FIELD_ITEM_STATUS);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param itemStatus the item_status to set
     */
    @JsonProperty(FIELD_ITEM_STATUS)
    private void setItemStatus(String itemStatus) {
        put(FIELD_ITEM_STATUS, itemStatus);
    }
}
