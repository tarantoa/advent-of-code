use std::collections::HashMap;

struct Point {
    x: i32,
    y: i32,
}

struct Line {
    p: Point,
    q: Point,
}

fn main() {
    let contents = std::fs::read_to_string("../data/day_05.txt")
        .expect("Failed to open data file");
}
