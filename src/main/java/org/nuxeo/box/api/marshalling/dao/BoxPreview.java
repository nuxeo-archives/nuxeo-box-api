package org.nuxeo.box.api.marshalling.dao;


import org.nuxeo.box.api.marshalling.exceptions.BoxRestException;

import java.io.InputStream;

/**
 * Preview of a file.
 */
public class BoxPreview extends BoxObject {

    public final static String MIN_WIDTH = "min_width";

    public final static String MIN_HEIGHT = "min_height";

    public final static String MAX_WIDTH = "max_width";

    public final static String MAX_HEIGHT = "max_height";

    public final static String PAGE = "page";

    private int firstPage = 1;

    private int lastPage = 1;

    private InputStream content;

    /**
     * Get the first page number.
     *
     * @return the first page number.
     */
    public Integer getFirstPage() {
        return this.firstPage;
    }

    /**
     * @param firstPage first page number
     */
    public void setFirstPage(Integer firstPage) {
        this.firstPage = firstPage;
    }

    /**
     * Get the last page number.
     *
     * @return the last page number
     */
    public Integer getLastPage() {
        return this.lastPage;
    }

    /**
     * Set the last page number.
     *
     * @param lastPage last page number
     */
    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    /**
     * Get content of the preview. Caller is responsible for closing the
     * InputStream.
     *
     * @return preview input stream.
     * @throws org.nuxeo.box.api.marshalling.exceptions.BoxRestException
     */
    public InputStream getContent() throws BoxRestException {
        return content;
    }

    /**
     * Set content.
     *
     * @param content content
     */
    public void setContent(InputStream content) {
        this.content = content;
    }

    /**
     * Get number of pages.
     *
     * @return number of pages
     */
    public Integer getNumPages() {
        return getLastPage() - getFirstPage() + 1;
    }

}
