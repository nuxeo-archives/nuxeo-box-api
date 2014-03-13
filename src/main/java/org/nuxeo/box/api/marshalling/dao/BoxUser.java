package org.nuxeo.box.api.marshalling.dao;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Box user.
 */
public class BoxUser extends BoxTypedObject {

    /**
     * Used when the user's enterprise role is admin.
     */
    public static final String ROLE_ADMIN = "admin";

    /**
     * Used when the user's enterprise role is co-admin.
     */
    public static final String ROLE_COADMIN = "coadmin";

    /**
     * Used when the user's enterprise role is normal user.
     */
    public static final String ROLE_USER = "user";

    /**
     * Used when user status is active
     */
    public static final String STATUS_ACTIVE = "active";

    /**
     * Used when user status is inactive
     */
    public static final String STATUS_INACTIVE = "inactive";

    /** Optional field parameters. */

    /**
     * The user's enterprise role. Can be admin, coadmin, or user
     */
    public static final String FIELD_ROLE = "role";

    /**
     * An array of key/value pairs set by the user's admin
     */
    public static final String FIELD_TRACKING_CODES = "tracking_codes";

    /**
     * Whether this user can see other enterprise users in its contact list
     */
    public static final String FIELD_CAN_SEE_MANAGED_USERS =
            "can_see_managed_users";

    /**
     * Whether to exempt this user from Enterprise device limits
     */
    public static final String FIELD_IS_SYNC_ENABLED = "is_sync_enabled";

    /**
     * Whether or not this user must use two-factor authentication
     */
    public static final String FIELD_IS_EXEMPT_FROM_DEVICE_LIMITS =
            "is_exempt_from_device_limits";

    /**
     * Whether or not this user must use two-factor authentication
     */
    public static final String FIELD_IS_EXEMPT_FROM_LOGIN_VERIFICATION =
            "is_exempt_from_login_verficiation";

    /**
     * Mini representation of this user's enterprise, including the ID of its
     * enterprise
     */
    public static final String FIELD_ENTERPRISE = "enterprise";

    public static final String FIELD_NAME = "name";

    public static final String FIELD_LOGIN = "login";

    public static final String FIELD_LANGUAGE = "language";

    public static final String FIELD_SPACE_AMOUNT = "space_amount";

    public static final String FIELD_SPACE_USED = "space_used";

    public static final String FIELD_MAX_UPLOAD_SIZE = "max_upload_size";

    public static final String FIELD_STATUS = "status";

    public static final String FIELD_JOB_TITLE = "job_title";

    public static final String FIELD_PHONE = "phone";

    public static final String FIELD_ADDRESS = "address";

    public static final String FIELD_AVATAR_URL = "avatar_url";

    public static final String FIELD_EXEMPT_FROM_DEVICE_LIMITS =
            "is_exempt_from_device_limits";

    public static final String FIELD_EXEMPT_FROM_LOGIN_VERIFICATION =
            "is_exempt_from_login_verification";

    public static final String FIELD_MY_TAGS = "my_tags";

    /**
     * Constructor.
     */
    public BoxUser() {
        setType(BoxResourceType.USER.toString());
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     *
     * @param obj
     */
    public BoxUser(BoxUser obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a
     * field.
     *
     * @param map
     */
    public BoxUser(Map<String, Object> map) {
        super(map);
    }

    /**
     * Get name of this user.
     *
     * @return name
     */
    @JsonProperty(FIELD_NAME)
    public String getName() {
        return (String) getValue(FIELD_NAME);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param name name
     */
    @JsonProperty(FIELD_NAME)
    private void setName(String name) {
        put(FIELD_NAME, name);
    }

    /**
     * Get the email address this user uses to login
     *
     * @return login
     */
    @JsonProperty(FIELD_LOGIN)
    public String getLogin() {
        return (String) getValue(FIELD_LOGIN);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param login login
     */
    @JsonProperty(FIELD_LOGIN)
    private void setLogin(String login) {
        put(FIELD_LOGIN, login);
    }

    /**
     * Get the user's enterprise role. The role can be {@link #ROLE_ADMIN},
     * {@link #ROLE_COADMIN} or {@link #ROLE_USER}
     *
     * @return the role
     */
    @JsonProperty(FIELD_ROLE)
    public String getRole() {
        return (String) getValue(FIELD_ROLE);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param role the role to set
     */
    @JsonProperty(FIELD_ROLE)
    private void setRole(String role) {
        put(FIELD_ROLE, role);
    }

    /**
     * Get the language of this user. This uses <a href="http://en.wikipedia
     * .org/wiki/ISO_639-1">ISO 639-1 Language Code</a>
     *
     * @return the language
     */
    @JsonProperty(FIELD_LANGUAGE)
    public String getLanguage() {
        return (String) getValue(FIELD_LANGUAGE);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param language the language to set
     */
    @JsonProperty(FIELD_LANGUAGE)
    private void setLanguage(String language) {
        put(FIELD_LANGUAGE, language);
    }

    /**
     * Get the user's total available space amount in bytes.
     *
     * @return the space_amount
     */
    @JsonProperty(FIELD_SPACE_AMOUNT)
    public Double getSpaceAmount() {
        return (Double) getValue(FIELD_SPACE_AMOUNT);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param spaceAmount the space_amount to set
     */
    @JsonProperty(FIELD_SPACE_AMOUNT)
    private void setSpaceAmount(Double spaceAmount) {
        put(FIELD_SPACE_AMOUNT, spaceAmount);
    }

    /**
     * Get the amount of space in use by the user.
     *
     * @return the space_used
     */
    @JsonProperty(FIELD_SPACE_USED)
    public Double getSpaceUsed() {
        return (Double) getValue(FIELD_SPACE_USED);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param spaceUsed the space_used to set
     */
    @JsonProperty(FIELD_SPACE_USED)
    private void setSpaceUsed(Double spaceUsed) {
        put(FIELD_SPACE_USED, spaceUsed);
    }

    /**
     * Get the maximum individual file size in bytes this user can have
     *
     * @return the max_upload_size
     */
    @JsonProperty(FIELD_MAX_UPLOAD_SIZE)
    public Double getMaxUploadSize() {
        return (Double) getValue(FIELD_MAX_UPLOAD_SIZE);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param max_upload_size the max_upload_size to set
     */
    @JsonProperty(FIELD_MAX_UPLOAD_SIZE)
    private void setMaxUploadSize(Double max_upload_size) {
        put(FIELD_MAX_UPLOAD_SIZE, max_upload_size);
    }

    /**
     * Get the tracking codes. This is an array of key/value pairs set by the
     * user's admin.
     *
     * @return the tracking_codes
     */
    @SuppressWarnings("unchecked")
    @JsonProperty(FIELD_TRACKING_CODES)
    public Map<String, String> getTrackingCodes() {
        return (Map<String, String>) getValue(FIELD_TRACKING_CODES);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param trackingCodes the tracking_codes to set
     */
    @JsonProperty(FIELD_TRACKING_CODES)
    private void setTrackingCodes(Map<String, String> trackingCodes) {
        put(FIELD_TRACKING_CODES, trackingCodes);
    }

    /**
     * Whether or not the user can see other enterprise users in the contact
     * list.
     *
     * @return the can_see_managed_users
     */
    @JsonProperty(FIELD_CAN_SEE_MANAGED_USERS)
    public Boolean canSeeManagedUsers() {
        return (Boolean) getValue(FIELD_CAN_SEE_MANAGED_USERS);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param canSeeManagedUsers the can_see_managed_users to set
     */
    @JsonProperty(FIELD_CAN_SEE_MANAGED_USERS)
    private void setCanSeeManagedUsers(Boolean canSeeManagedUsers) {
        put(FIELD_CAN_SEE_MANAGED_USERS, canSeeManagedUsers);
    }

    /**
     * Whether or not this user can use Box Sync.
     *
     * @return the is_sync_enabled
     */
    @JsonProperty(FIELD_IS_SYNC_ENABLED)
    public Boolean isSyncEnabled() {
        return (Boolean) getValue(FIELD_IS_SYNC_ENABLED);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param isSyncEnabled the is_sync_enabled to set
     */
    @JsonProperty(FIELD_IS_SYNC_ENABLED)
    private void setSyncEnabled(Boolean isSyncEnabled) {
        put(FIELD_IS_SYNC_ENABLED, isSyncEnabled);
    }

    /**
     * Get status of the user. This String can be {@link #STATUS_ACTIVE} or
     * {@link #STATUS_INACTIVE}
     *
     * @return the status
     */
    @JsonProperty(FIELD_STATUS)
    public String getStatus() {
        return (String) getValue(FIELD_STATUS);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param status the status to set
     */
    @JsonProperty(FIELD_STATUS)
    private void setStatus(String status) {
        put(FIELD_STATUS, status);
    }

    /**
     * Get the user's job title.
     *
     * @return the job_title
     */
    @JsonProperty(FIELD_JOB_TITLE)
    public String getJobTitle() {
        return (String) getValue(FIELD_JOB_TITLE);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param jobTitle the job_title to set
     */
    @JsonProperty(FIELD_JOB_TITLE)
    private void setJob_title(String jobTitle) {
        put(FIELD_JOB_TITLE, jobTitle);
    }

    /**
     * Get the user's phone number.
     *
     * @return the phone
     */
    @JsonProperty(FIELD_PHONE)
    public String getPhone() {
        return (String) getValue(FIELD_PHONE);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param phone the phone to set
     */
    @JsonProperty(FIELD_PHONE)
    private void setPhone(String phone) {
        put(FIELD_PHONE, phone);
    }

    /**
     * Get the user's address.
     *
     * @return the address
     */
    @JsonProperty(FIELD_ADDRESS)
    public String getAddress() {
        return (String) getValue(FIELD_ADDRESS);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param address the address to set
     */
    @JsonProperty(FIELD_ADDRESS)
    private void setAddress(String address) {
        put(FIELD_ADDRESS, address);
    }

    /**
     * get the URL for this user's avatar image.
     *
     * @return the avatar_url
     */
    @JsonProperty(FIELD_AVATAR_URL)
    public String getAvatarUrl() {
        return (String) getValue(FIELD_AVATAR_URL);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param avatarUrl the avatar_url to set
     */
    @JsonProperty(FIELD_AVATAR_URL)
    private void setAvatarUrl(String avatarUrl) {
        put(FIELD_AVATAR_URL, avatarUrl);
    }

    /**
     * Whether to exempt this user from Enterprise device limits.
     *
     * @return the is_exempt_from_device_limits
     */
    @JsonProperty(FIELD_EXEMPT_FROM_DEVICE_LIMITS)
    public Boolean isExemptFromDeviceLimits() {
        return (Boolean) getValue(FIELD_EXEMPT_FROM_DEVICE_LIMITS);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param isExemptFromDeviceLimits the is_exempt_from_device_limits to set
     */
    @JsonProperty(FIELD_EXEMPT_FROM_DEVICE_LIMITS)
    private void setExemptFromDeviceLimits(Boolean isExemptFromDeviceLimits) {
        put(FIELD_EXEMPT_FROM_DEVICE_LIMITS, isExemptFromDeviceLimits);
    }

    /**
     * Whether or not this user must use two-factor authentication.
     *
     * @return the is_exempt_from_login_verification
     */
    @JsonProperty(FIELD_EXEMPT_FROM_LOGIN_VERIFICATION)
    public Boolean isExemptFromLoginVerification() {
        return (Boolean) getValue(FIELD_EXEMPT_FROM_LOGIN_VERIFICATION);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param isExemptFromLoginVerification the
     *                                      is_exempt_from_login_verification
     *                                      to set
     */
    @JsonProperty(FIELD_EXEMPT_FROM_LOGIN_VERIFICATION)
    private void setExemptFromLoginVerification(boolean
            isExemptFromLoginVerification) {
        put(FIELD_EXEMPT_FROM_LOGIN_VERIFICATION,
                isExemptFromLoginVerification);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param enterprise enterprise
     */
    @JsonProperty(FIELD_ENTERPRISE)
    private void setEnterprise(BoxEnterprise enterprise) {
        put(FIELD_ENTERPRISE, enterprise);
    }

    /**
     * Get the enterprise this user belongs to if available, null otherwise.
     *
     * @return enterprise
     */
    @JsonProperty(FIELD_ENTERPRISE)
    public BoxEnterprise getEnterprise() {
        return (BoxEnterprise) getValue(FIELD_ENTERPRISE);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus
     * .org">Jackson JSON processer</a>}
     *
     * @param myTags my_tags
     */
    @JsonProperty(FIELD_MY_TAGS)
    private void setMyTags(String[] myTags) {
        put(FIELD_MY_TAGS, myTags);
    }

    /**
     * Get set of all tags on items that are visible by this user. Note this
     * is not tags of the "BoxUser" object.
     *
     * @return tags
     */
    @JsonProperty(FIELD_MY_TAGS)
    public String[] getMyTags() {
        return (String[]) getValue(FIELD_MY_TAGS);
    }
}
