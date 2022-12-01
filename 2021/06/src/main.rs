use std::str::FromStr;

fn main() {
    let contents = std::fs::read_to_string("../data/day_06.txt")
        .expect("Failed to open data file");

    let initial_state = contents.trim().split(',')
        .map(|fish_raw| usize::from_str(fish_raw).unwrap())
        .fold(vec![0; 9], |mut state, fish| {
            state[fish] += 1;
            state
        });

    // -- Part 1 --
    let num_days = 80;
    let end_state = model_lifecycle(initial_state.clone(), num_days)
        .into_iter()
        .fold(0, |sum, cur| sum + cur);
    println!("{} lanternfish after {} days", end_state, num_days);

    // -- Part 2 --
    let num_days = 256;
    let end_state = model_lifecycle(initial_state, num_days)
        .into_iter()
        .fold(0, |sum, cur| sum + cur);
    println!("{} lanternfish after {} days", end_state, num_days);
}

fn model_lifecycle(mut initial_state: Vec<i64>, days_remaining: usize) -> Vec<i64> {
    let num_stages = initial_state.len();
    for _ in 0..days_remaining {
        let new_fish = initial_state[0];
        for stage in 0..num_stages - 1 {
            initial_state[stage] = initial_state[stage + 1];
        }
        initial_state[num_stages - 1] = new_fish;
        initial_state[6] += new_fish;
    }

    initial_state
}
