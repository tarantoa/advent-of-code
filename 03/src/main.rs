fn main() {
    // open code file
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
                .zip(
                    String::from(code)
                        .chars()
                        .map(|c| c.to_digit(10).unwrap())
                )
                .map(|(&freq, c)| {
                    if c == 1 { freq + 1 } else { freq - 1 }
                })
                .collect::<Vec<i32>>()
        })
        .iter()
        .map(|&x| {
            if x > 0 { '1' } else { '0' }
        })
        .collect::<Vec<char>>();

    let epsilon = gamma
        .iter()
        .map(|&x| {
            if x == '1' { '0' } else { '1' }
        })
        .collect::<Vec<char>>();

    let gamma_value = gamma.into_iter().collect::<String>();
    let gamma_value = isize::from_str_radix(&gamma_value[..], 2)
        .unwrap();
    let epsilon_value = epsilon.into_iter().collect::<String>();
    let epsilon_value = isize::from_str_radix(&epsilon_value[..], 2)
        .unwrap();

    println!(
        "gamma = {}, epsilon = {}, result = {}",
        gamma_value,
        epsilon_value,
        gamma_value * epsilon_value
    );
}
