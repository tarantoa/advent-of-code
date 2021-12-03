fn main() {
    // open codes file
    let codes = std::fs::read_to_string("../data/gamma_readings.txt")
        .unwrap();
    let codes = codes
        .split('\n')
        .collect::<Vec<&str>>();

    // -- Part 1 --
    let code_length = 12;
    let gamma = codes
        .iter()
        .fold(vec![0; code_length], |freqs, &code| {
            freqs 
                .iter()
                .zip( // convert char to i32
                    String::from(code)
                        .chars()
                        .map(|c| c.to_digit(10).unwrap())
                )
                .map(|(&freq, c)| { // count up for 1s at each index, down for 0s
                    if c == 1 { freq + 1 } else { freq - 1 }
                })
                .collect::<Vec<i32>>()
        })
        .iter()
        .map(|&x| { // map to binary value
            if x > 0 { '1' } else { '0' }
        })
        .collect::<String>(); // aggregate to string
    let epsilon = gamma 
        .chars()
        .map(|c| { // flip bit values
            if c == '1' { '0' } else { '1' }
        })
        .collect::<String>();

    let gamma_value = isize::from_str_radix(&gamma[..], 2)
        .unwrap();
    let epsilon_value = isize::from_str_radix(&epsilon[..], 2)
        .unwrap();

    println!(
        "gamma = {}, epsilon = {}, result = {}",
        gamma_value,
        epsilon_value,
        gamma_value * epsilon_value
    );

    // -- Part 2 --
}
