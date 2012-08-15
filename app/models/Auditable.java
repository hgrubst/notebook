package models;

import java.util.Date;

public interface Auditable {

    Date getCreatedDate();

    void setCreatedDate(Date creationDate);

    Date getLastModifiedDate();

    void setLastModifiedDate(Date lastModifiedDate);
	
}
