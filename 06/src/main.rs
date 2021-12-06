use std::str::FromStr;

fn main() {
    let contents = std::fs::read_to_string("../data/day_06.txt")
        .expect("Failed to open data file");

    let mut initial_state = vec![0; 9];
    contents.trim().split(',').for_each(|val| {
        let idx = usize::from_str(val).unwrap();
        initial_state[idx] += 1;
    });

    println!("{:?}", initial_state);

    // -- Part 1 --
    let end_state = model_lifecycle(initial_state.clone(), 80)
        .into_iter()
        .fold(0, |sum, cur| sum + cur);

    println!("{} lanternfish after 80 days", end_state);

    // -- Part 2 --
    let end_state = model_lifecycle(initial_state.clone(), 256)
        .into_iter()
        .fold(0, |sum, cur| sum + cur);

    println!("{}", end_state);
}

fn model_lifecycle(mut initial_state: Vec<i64>, days_remaining: usize) -> Vec<i64> {
    for _ in 0..days_remaining {
        let mut current_state: Vec::<i64> = vec![0; 9];
        let new_fish = initial_state[0] as i64;
        for stage in 0..initial_state.len() - 1 {
            current_state[stage] = initial_state[stage + 1] as i64;
        }
        current_state[initial_state.len() - 1] = new_fish as i64;
        current_state[6] += new_fish;
        initial_state = current_state;
    }

    initial_state
}
