package admission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

public class InpComboModel implements ComboBoxModel {

	public Map<String, String> model;

	public List<String> keyList;

	public String selectedItem;

	public InpComboModel(Map<String, String> src) {
		model = src;
		keyList = new ArrayList<String>();
		keyList.add("-");
		for (String key : src.keySet()) {
			keyList.add(key);
		}
		model.put("-", "");
	}

	@Override
	public int getSize() {
		return model.size();
	}

	@Override
	public Object getElementAt(int index) {
		return model.get(keyList.get(index));
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSelectedItem(Object anItem) {
		for (Entry<String, String> set : model.entrySet()) {
			if (set.getValue().equals(anItem)) {
				selectedItem = set.getKey();
			}
		}
	}

	@Override
	public Object getSelectedItem() {
		return model.get(selectedItem);
	}

}
