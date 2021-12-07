use std::str::FromStr;

fn main() {
    let data = std::fs::read_to_string("../data/day_07.txt")
        .expect("Failed to open data file");
    let mut crab_positions = data.trim().split(',')
        .map(|position_raw| i32::from_str(position_raw).unwrap())
        .collect::<Vec<i32>>();
    crab_positions.sort();

    // -- Part 1 --
    let median_index = crab_positions.len() / 2;
    let median = match crab_positions.len() % 2 == 0 {
        true => (crab_positions[median_index] + crab_positions[median_index - 1]) / 2, 
        false => crab_positions[median_index],
    };
    
    let total_fuel = crab_positions.iter()
        .fold(0, |fuel_cost, curr| fuel_cost + (median - curr).abs());
    println!("total fuel cost for aligning crabs = {}", total_fuel);

}
