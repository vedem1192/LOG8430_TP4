package tp4;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class ItemHelper {
	
	public static final List<Document> toDBDocument(String id, Item[] items) {
		List<Document> documentItem = new ArrayList<>();
		
		for(Item item : items) {
			Document document = new Document();
			document.append("receiptID", id)
					.append("name", item.getName())
					.append("price", item.getPrice())
					.append("quatity", item.getQuantity());
			
			documentItem.add(document);
		}

		return documentItem;
	}
	
	public static final Document toDBDocument(String id, Item item) {
		Document documentItem = new Document();

			documentItem.append("receiptID", id)
					.append("name", item.getName())
					.append("price", item.getPrice())
					.append("quatity", item.getQuantity());
		

		return documentItem;
	}
}
