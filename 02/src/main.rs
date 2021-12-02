fn main() {
    // Read input file
    let inputs = std::fs::read_to_string("../data/movement_inputs.txt")
        .expect("Failed to open input file");

    // -- Part 1 --
    // (horizontal_offset, depth)
    let position: (i32, i32) = inputs
        .split('\n')
        .fold((0, 0), |acc, input| {
            let input = String::from(input);
            let mut split = input.split_whitespace();
            let direction = split
                .next()
                .unwrap();
            let distance = split
                .next()
                .unwrap()
                .parse::<i32>()
                .unwrap();

            match &direction[..] {
                "forward" => (acc.0 + distance, acc.1),
                "down" => (acc.0, acc.1 + distance),
                "up" => (acc.0, acc.1 - distance),
                _ => acc,
            }
        });
    println!("Final position: {}", position.0 * position.1);

    // -- Part 2 --
    // (horizontal_offset, depth, aim)
    let position_with_aim: (i32, i32, i32) = inputs
        .split('\n')
        .fold((0, 0, 0), |acc, input| {
            let input = String::from(input);
            let mut split = input.split_whitespace();
            let direction = split
                .next()
                .unwrap();
            let distance = split
                .next()
                .unwrap()
                .parse::<i32>()
                .unwrap();

            match &direction[..] {
                "forward" => (acc.0 + distance, acc.1 + (acc.2 * distance), acc.2),
                "down" => (acc.0, acc.1, acc.2 + distance),
                "up" => (acc.0, acc.1, acc.2 - distance),
                _ => acc,
            }
        });
    println!("Final position: {}", position_with_aim.0 * position_with_aim.1);
}