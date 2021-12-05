use std::str::FromStr;
use std::collections::HashSet;

#[derive(Debug)]
struct Card {
    lines: Vec<Vec<i32>>,
    marked: HashSet<i32>,
}

impl Card {
    fn get_score(&self) -> i32 {
        let mut sum= 0;
        for row in &self.lines {
            for value in row {
                if !self.marked.contains(&value) {
                    sum += value;
                } 
            }
        }

        sum 
    }
}

fn main() {
    // -- Part 1 --
    let contents = std::fs::read_to_string("../data/day_04.txt")
        .expect("Failed to load data file");
    let mut current_line = contents.split('\n');
    let called_numbers = current_line
        .next()
        .unwrap();

    current_line.next(); 

    let mut cards: Vec<Card> = vec![];
    let mut buffer: Vec<Vec<i32>> = Vec::with_capacity(5);
    loop {
        match current_line.next() {
            Some(line) => {
                let line_vals = line.split_whitespace()
                    .into_iter()
                    .map(|val| val.parse::<i32>().unwrap())
                    .collect::<Vec<i32>>();
                if line_vals.len() > 0 {
                    buffer.push(line_vals);
                } else {
                    cards.push(Card {
                        lines: buffer.to_vec(),
                        marked: std::collections::HashSet::new(),
                    });
                    buffer.clear();
                }
            },
            None => { break; },
        }
    }

    let called_numbers = called_numbers.split(',')
        .into_iter()
        .map(|val| i32::from_str(val.trim()).unwrap())
        .collect::<Vec<i32>>();
    
    let mut idx = 0;
    let mut winning_card: Option<&Card>= None;
    let num_cards = cards.len();
    while idx < called_numbers.len() && winning_card.is_none() {
        for card_idx in 0..num_cards {
            if winning_card.is_none() {
                winning_card = match mark(&mut cards[card_idx], called_numbers[idx]) {
                    Some((row, col)) => {
                        match check(&mut cards[card_idx], (row, col)) {
                            true => {
                                Some(&cards[card_idx])
                            },
                            false => None,
                        }
                    },
                    None => None,
                }
            } else {
                break;
            }
        }
        idx += 1;
    }

    println!("score = {}", winning_card.unwrap().get_score() * called_numbers[idx - 1] as i32);
}

fn mark(card: &mut Card, val: i32) -> Option<(usize, usize)> {
    for row in 0..card.lines.len() {
        for col in 0..card.lines[row].len() {
            if card.lines[row][col] == val {
                card.marked.insert(val);
                return Some((row, col));
            }
        }
    }
    None
}

fn check(card: &mut Card, last_marked: (usize, usize)) -> bool {
    let mut winning_row = true;
    for value in &card.lines[last_marked.0] {
        winning_row &= card.marked.contains(&value);
    }
    if winning_row {
        return true;
    }

    let mut winning_col = true;
    for row in 0..card.lines.len() {
        winning_col &= card.marked.contains(&card.lines[row][last_marked.1]);
    }
    if winning_col {
        return true;
    }

    false
}