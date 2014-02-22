package com.nuxeo.box.api.marshalling.dao;

/**
 * When inviting a user as a collaborator you specify the level of access
 * that user has to your files. This class defines the permission levels.
 * There are two
 * groups of permission levels. One is available to everyone. The other is
 * available only to business and enterprise users. For the latter group of
 * permission,
 * they must be enabled by the admin in the 'User Collaboration Settings' tab
 * in "Admin Settings" from the "Admin Console". If any one of the permission
 * levels
 * is not available for you to choose, then your admin has most likely
 * disabled this access level for the Enterprise account
 */
public class BoxCollaborationRole {

    /**
     * An Editor has full read/ write access to a folder. This is available
     * to everyone. They can view and download the contents of the folder,
     * as well as
     * upload new content into the folder. They have permission to delete
     * items, edit items, comment of files, generate a shared link for items
     * in the folder,
     * and create tags. By default an Editor will be able to invite new
     * collaborators to a folder, however an editor cannot manage users
     * currently existing in
     * the folder.
     */
    public static final String EDITOR = "editor";

    /**
     * A Viewer has full read access to a folder. This is available to
     * everyone. So they will be able to preview any item using the
     * integrated content viewer,
     * and will be able to download any item in the folder. A Viewer can
     * generate a shared link for any item in the folder as well as make
     * comments on items. A
     * viewer will not be able to add tags, invite new collaborators, upload,
     * or edit items in the folder.
     */
    public static final String VIEWER = "viewer";

    /**
     * A Previewer only has limited read access. This is only available for
     * business and enterprise users. This permission level allows a user to
     * view the items
     * in the folder using the integrated content viewer or a viewing
     * application from the OpenBox directory such as Scribd. They will have
     * no other access to
     * the files and will not be able to download, edit,
     * or upload into the folder.
     */
    public static final String PREVIEWER = "previewer";

    /**
     * An Uploader is the most limited access that a user can have in a
     * folder and provides limited write access. This is only available for
     * business and
     * enterprise users.A user assigned uploader will see the items in a
     * folder but will not be able to download or view the items. The only
     * action available
     * will be to upload content into the folder. If an Uploader uploads an
     * item with the same name as an existing item in the folder,
     * the file will be updated
     * and the existing version will be moved into the version history.
     */
    public static final String UPLOADER = "uploader";

    /**
     * This access level is a combination of Previewer and Uploader. This is
     * only available for business and enterprise users.A user with this
     * access level will
     * be able to preview files in the folder using the integrated content
     * viewer or a viewing application from the OpenBox directory such as
     * Scribd and will
     * also be able to upload items into the folder. If a Previewer-Uploader
     * uploads an item with the same name as an existing item in the folder,
     * the file will
     * be updated and the existing version will be moved into the version
     * history. They will have no other access to the files and will not be
     * able to download
     * or edit items in the folder.
     */
    public static final String PREVIEWER_UPLOADER = "previewer-uploader";

    /**
     * This access level is a combination of Viewer and Uploader. This is
     * only available for business and enterprise users.A Viewer-Uploader has
     * full read
     * access to a folder and limited write access. They will be able to
     * preview any item using the integrated content viewer,
     * and will be able to download any
     * item in the folder. They can generate a shared link for any item in
     * the folder as well as make comments on items. A Viewer-Uploader will
     * also be able to
     * upload content into the folder. If a Viewer-Uploader uploads an item
     * with the same name as an existing item in the folder,
     * the file will be updated and
     * the existing version will be moved into the version history. They will
     * not be able to add tags, invite new collaborators,
     * or edit items in the folder.
     */
    public static final String VIEWER_UPLOADER = "viewer-uploader";

    /**
     * A Co-Owner has all of the functional read/ write access that an Editor
     * does. This is only available for business and enterprise users.This
     * permission
     * level has the added ability of being able to manage users in the
     * folder. A Co-Owner can add new collaborators,
     * change collaborators access, and remove
     * collaborators (they will not be able to manipulate the owner of the
     * folder or transfer ownership to another user).
     */
    public static final String CO_OWNER = "co-owner";
}
