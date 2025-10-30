package factorymethod;

import java.util.HashMap;

public class ItemFactory extends Factory {
    private static class ItemData {
        int maxCount;
        int currentCount;
        ItemData(int maxCount){
            this.maxCount = maxCount;
        }
    }

    private final HashMap<String, ItemData> repository;

    public ItemFactory() {
        repository = new HashMap<String, ItemData>();

        repository.put("sword", new ItemData(3));
        repository.put("shield", new ItemData(2));
        repository.put("bow", new ItemData(1));
    }

    @Override
    public void postprocessItem(String name) {
        ItemData itemData = repository.get(name);
        if(itemData != null) itemData.currentCount++;
    }

    @Override
    public Item createItem(String name) {
        return switch (name) {
            case "sword" -> new Sword();
            case "shield" -> new Shield();
            case "bow" -> new Bow();
            default -> null;
        };
    }


    @Override
    public boolean isCreatable(String name) {
        ItemData itemData = repository.get(name);

        if(itemData == null){
            System.out.println("알 수 없는 아이템입니다.");
            return false;
        }

        if(itemData.currentCount >= itemData.maxCount){
            System.out.println("품절 된 아이템입니다.");
            return false;
        }

        return true;
    }
}
