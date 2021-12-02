use std::env;
use std::fs;

fn main() {
    // -- Part 1 --
    let args: Vec<String> = env::args().collect();

    // read data file
    let filename = &args[1];
    let contents = fs::read_to_string(filename)
        .expect("Unable to open data file");

    // convert to vec<int>
    let depth_values: Vec<i32> = contents
        .split_whitespace()
        .map(|val| val.parse::<i32>().unwrap())
        .collect();

    // count number of values larger than previous
    let mut increasing_intervals = 0;
    for idx in 1..depth_values.len() {
        if depth_values[idx] > depth_values[idx - 1] {
            increasing_intervals += 1;
        }
    }
    println!("There are {} increasing depth intervals", increasing_intervals);

    // -- Part 2 --

    increasing_intervals = 0;
    for idx in 3..depth_values.len() {
        if depth_values[idx] - depth_values[idx - 3] > 0 {
            increasing_intervals += 1;
        }
    }
    println!(
        "There are {} increasing depth sample intervals", increasing_intervals
    );
}
