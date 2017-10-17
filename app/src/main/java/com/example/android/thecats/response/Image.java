package com.example.android.thecats.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by fbrsw on 11.10.2017.
 */

@Root(name = "image", strict = false)
public class Image {

    @Element(name = "url")
    private String url;

    @Element(name = "id")
    private String id;

    @Element(name = "source_url", required = false)
    private String source_url;

    @Element(name = "sub_id", required = false)
    private String sub_id;

    @Element(name = "created", required = false)
    private String created;

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getSource_url ()
    {
        return source_url;
    }

    public void setSource_url (String source_url)
    {
        this.source_url = source_url;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", source_url = "+source_url+", url = "+url+"]";
    }

}
