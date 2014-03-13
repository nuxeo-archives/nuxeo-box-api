package org.nuxeo.box.api.marshalling.dao;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.nuxeo.box.api.utils.ISO8601DateParser;

import java.util.Date;
import java.util.Map;

/**
 * Box File object
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,
        property = "type", defaultImpl = BoxFile.class)
public class BoxFile extends BoxItem {

    public final static String FIELD_SHA1 = "sha1";

    public final static String FIELD_VERSION_NUMBER = "version_number";

    public final static String FIELD_COMMENT_COUNT = "comment_count";

    public final static String FIELD_CONTENT_CREATED_AT = "content_created_at";

    public final static String FIELD_CONTENT_MODIFIED_AT =
            "content_modified_at";

    /**
     * Constructor.
     */
    public BoxFile() {
        setType(BoxResourceType.FILE.toString());
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxFile(BoxFile obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a
     * field.
     *
     * @param map
     */
    public BoxFile(Map<String, Object> map) {
        super(map);
    }

    /**
     * Get sha1 of the file.
     *
     * @return sha1 of the file.
     */
    @JsonProperty(FIELD_SHA1)
    public String getSha1() {
        return (String) getValue(FIELD_SHA1);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param sha1 sha1
     */
    @JsonProperty(FIELD_SHA1)
    private void setSha1(String sha1) {
        put(FIELD_SHA1, sha1);
    }

    @JsonProperty(FIELD_CONTENT_CREATED_AT)
    public String getContentCreatedAt() {
        return (String) getValue(FIELD_CONTENT_CREATED_AT);
    }

    /**
     * Return the date content created on local machine,
     * if this date was not provided when uploading the file,
     * this will be the time file was uploaded.
     *
     * @return
     */
    public Date dateContentCreatedAt() {
        return ISO8601DateParser.parseSilently(getContentCreatedAt());
    }

    @JsonProperty(FIELD_CONTENT_CREATED_AT)
    private void setContentCreatedAt(String createdAt) {
        put(FIELD_CONTENT_CREATED_AT, createdAt);
    }

    @JsonProperty(FIELD_CONTENT_MODIFIED_AT)
    public String getContentModifiedAt() {
        return (String) getValue(FIELD_CONTENT_MODIFIED_AT);
    }

    /**
     * Return the date content last modified on local machine,
     * if this date was not provided when uploading the file,
     * this will be the time file was uploaded.
     *
     * @return
     */
    public Date dateContentModifieddAt() {
        return ISO8601DateParser.parseSilently(getContentModifiedAt());
    }

    @JsonProperty(FIELD_CONTENT_MODIFIED_AT)
    private void setContentModifiedAt(String modifiedAt) {
        put(FIELD_CONTENT_MODIFIED_AT, modifiedAt);
    }

    /**
     * Get version number of the file.
     *
     * @return version number of the file.
     */
    @JsonProperty(FIELD_VERSION_NUMBER)
    public String getVersionNumber() {
        return (String) getValue(FIELD_VERSION_NUMBER);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param versionNumber version number
     */
    @JsonProperty(FIELD_VERSION_NUMBER)
    private void setVersionNumber(String versionNumber) {
        put(FIELD_VERSION_NUMBER, versionNumber);
    }

    /**
     * Get comment count of the file.
     *
     * @return comment count of the file.
     */
    @JsonProperty(FIELD_COMMENT_COUNT)
    public Integer getCommentCount() {
        return (Integer) getValue(FIELD_COMMENT_COUNT);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param commentCount comment count
     */
    @JsonProperty(FIELD_COMMENT_COUNT)
    private void setCommentCount(Integer commentCount) {
        put(FIELD_COMMENT_COUNT, commentCount);
    }
}
