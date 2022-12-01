package aoc;

import java.util.ArrayList;
import java.util.List;

public class Elf implements Comparable<Elf> {

  private List<Long> food;
  private Long calories;

  public static ElfBuilder newBuilder() {
    return new ElfBuilder();
  }

  private Elf(final List<Long> food) {
    if (food == null) {
      throw new IllegalArgumentException("food cannot be null.");
    }
    this.food = food;
    this.calories = food.stream().reduce(0L, (calorieTotal, foodCalories) -> calorieTotal + foodCalories);
  }

  public List<Long> getFood() {
    return food;
  }

  public Long getCalories() {
    return calories;
  }

  @Override
  public int compareTo(Elf other) {
    if (other.getCalories() != calories) {
      return other.getCalories().compareTo(calories);
    }
    return Integer.compare(other.getFood().size(), food.size());
  }

  @Override
  public String toString() {
    return String.format("Elf {%d, %d}", this.food.size(), this.calories);
  }

  public static final class ElfBuilder {

    private List<Long> food;
    private Long calories;

    private ElfBuilder() {
      food = new ArrayList<>();
      calories = 0L;
    }

    public ElfBuilder withFood(List<Long> food) {
      if (food != null) {
        this.food = food;
      }
      return this;
    }

    public ElfBuilder addFoodItem(Long food) {
      if (food < 0) {
        throw new IllegalArgumentException("food cannot be a negative value.");
      }
      this.food.add(food);
      return this;
    }

    public Elf build() {
      return new Elf(food);
    }
  }

}
