fn main() {
    // open codes file
    let codes = std::fs::read_to_string("../data/gamma_readings.txt").unwrap();
    let codes = codes.split('\n').collect::<Vec<&str>>();
    let code_length = 12;

    // -- Part 1 --
    let all_bit_freqs = codes.iter().fold(vec![0; code_length], |freqs, &code| {
        freqs
            .iter()
            .zip(
                String::from(code)
                    .trim()
                    .chars()
                    .map(|c| c.to_digit(10).unwrap()),
            )
            .map(|(&freq, c)| if c == 1 { freq + 1 } else { freq - 1 }
            )
            .collect::<Vec<i32>>()
    });
    let gamma = all_bit_freqs
        .iter()
        .map(|&x| {
            if x > 0 {
                '1'
            } else {
                '0'
            }
        })
        .collect::<String>(); 
    let epsilon = gamma
        .chars()
        .map(|c| {
            if c == '1' {
                '0'
            } else {
                '1'
            }
        })
        .collect::<String>();

    let gamma_value = isize::from_str_radix(&gamma[..], 2).unwrap();
    let epsilon_value = isize::from_str_radix(&epsilon[..], 2).unwrap();

    println!(
        "gamma = {}, epsilon = {}, result = {}",
        gamma_value,
        epsilon_value,
        gamma_value * epsilon_value
    );

    // -- Part 2 --
    let mut current_codes = codes.clone();
    for idx in 0..code_length {
        if current_codes.len() > 1 {
            let (zeroes, ones) = find_index_bit_freq(&current_codes, idx);
            let most_frequent = if ones >= zeroes { '1' } else { '0' };

            current_codes = filter_codes(current_codes, most_frequent, idx);
        } else {
            break;
        }
    }
    let oxygen = convert_singleton_to_decimal(current_codes);

    let mut current_codes = codes;
    for idx in 0..code_length {
        if current_codes.len() > 1 {
            let (zeroes, ones) = find_index_bit_freq(&current_codes, idx);
            let most_frequent = if zeroes >= ones { '0' } else { '1' };

            current_codes = filter_codes(current_codes, most_frequent, idx);
        } else {
            break;
        }
    }
    let c_02 = convert_singleton_to_decimal(current_codes);

    println!("oxygen = {}, c_02 = {}, Life support: {}", oxygen, c_02, oxygen * c_02);
}

fn find_index_bit_freq(codes: &Vec<&str>, position: usize) -> (i32, i32) {
    codes.iter().fold((0, 0), |freqs, code| {
        match code.as_bytes()[position] as char {
            '1' => (freqs.0, freqs.1 + 1),
            '0' => (freqs.0 + 1, freqs.1),
            _ => freqs,
        }
    })
}

fn filter_codes(codes: Vec<&str>, value: char, position: usize) -> Vec<&str> {
    codes
        .into_iter()
        .filter(|code| (code).as_bytes()[position] as char == value)
        .collect::<Vec<&str>>()
}

fn convert_singleton_to_decimal(code: Vec<&str>) -> isize {
    let unwrapped = code.into_iter().next().unwrap();
    isize::from_str_radix(unwrapped.trim(), 2).unwrap()
}