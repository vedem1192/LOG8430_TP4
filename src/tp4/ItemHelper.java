package tp4;

import org.bson.Document;

public class ItemHelper {
	
	public static final Document toDBDocument(Item item) {
		Document documentItem = new Document();
		
		documentItem.append("name", item.getName())
					.append("price", item.getPrice())
					.append("quatity", item.getQuantity());
		
		return documentItem;
	}
}
