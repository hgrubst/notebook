package repositories.listener;

import java.util.Date;

import models.Auditable;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.stereotype.Component;

@Component
public class AuditListener extends AbstractMongoEventListener<Auditable>{

	@Override
	public void onBeforeConvert(Auditable source) {
		Date now = new Date();
		
		
		if(source.getCreatedDate() == null){
			source.setCreatedDate(now);
		}
		
		source.setLastModifiedDate(now);
	}
	
}
