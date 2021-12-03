fn main() {
    // Read input file
    let inputs = std::fs::read_to_string("../data/movement_inputs.txt")
        .expect("Failed to open input file");

    // -- Part 1 --
    // (horizontal_offset, depth)
    let position: (i32, i32) = inputs
        .split('\n')
        .map(|line| {
            let mut split = line.split_whitespace();
            let direction = split
                .next()
                .unwrap();
            let distance = split
                .next()
                .unwrap()
                .parse::<i32>()
                .unwrap();

            (direction, distance)
        })
        .fold((0, 0), |acc, input| {
            match &input.0[..] {
                "forward" => (acc.0 + input.1, acc.1),
                "down" => (acc.0, acc.1 + input.1),
                "up" => (acc.0, acc.1 - input.1),
                _ => acc,
            }
        });
    println!("Final position: {}", position.0 * position.1);

    // -- Part 2 --
    // (horizontal_offset, depth, aim)
    let position_with_aim: (i32, i32, i32) = inputs
        .split('\n')
        .map(|line| {
            let mut split = line.split_whitespace();
            let direction = split
                .next()
                .unwrap();
            let distance = split
                .next()
                .unwrap()
                .parse::<i32>()
                .unwrap();

            (direction, distance)
        })
        .fold((0, 0, 0), |acc, input| {
            match &input.0[..] {
                "forward" => (acc.0 + input.1, acc.1 + (acc.2 * input.1), acc.2),
                "down" => (acc.0, acc.1, acc.2 + input.1),
                "up" => (acc.0, acc.1, acc.2 - input.1),
                _ => acc,
            }
        });
    println!("Final position: {}", position_with_aim.0 * position_with_aim.1);
}