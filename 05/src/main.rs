use std::collections::HashMap;
use std::str::FromStr;

use itertools::Itertools;
use lazy_static::lazy_static;
use regex::Regex;

#[derive(Debug, PartialEq, Eq, Hash)]
struct Point {
    x: i32,
    y: i32,
}

#[derive(Debug)]
struct Line {
    p: Point,
    q: Point,
}

impl Line {
    fn to_points(&self) -> Vec<Point> {
        let mut points: Vec<Point> = vec![];
        if self.p.x == self.q.x {
            let min = std::cmp::min(self.p.y, self.q.y);
            let max = std::cmp::max(self.p.y, self.q.y);

            (min..max+1).map(|y_val| Point { x: self.p.x, y: y_val })
                .for_each(|point| points.push(point));
        } else if self.p.y == self.q.y {
            let min = std::cmp::min(self.p.x, self.q.x);
            let max = std::cmp::max(self.p.x, self.q.x);

            (min..max+1).map(|x_val| Point { x: x_val, y: self.p.y })
                    .for_each(|point| points.push(point));
        } else {
            let x_direction = match self.p.x > self.q.x {
                true => -1,
                false => 1,
            };
            let y_direction = match self.p.y > self.q.y {
                true => -1,
                false => 1,
            };

            let mut x = self.p.x;
            let mut y = self.p.y;
            while x != self.q.x && y != self.q.y {
                points.push(Point { x, y });
                if x != self.q.x { x += x_direction; }
                if y != self.q.y { y += y_direction; }
            }
            points.push(Point { x, y });
        }

        points
    }
}

fn main() {
    lazy_static! { // ensures regex is compiled exactly once
        static ref RE: Regex = Regex::new("(\\d+,\\d+) -> (\\d+,\\d+)").unwrap();
    }

    let contents = std::fs::read_to_string("../data/day_05.txt")
        .expect("Failed to open data file");
    let textlines = contents.split('\n')
        .filter(|line| RE.is_match(line))
        .map(|line| String::from(line))
        .map(|mut line| {
            line.truncate(line.len() - 1);
            line
        })
        .collect::<Vec<String>>();

    let line_segments = textlines.iter()
        .map(|textline| RE.captures(textline).unwrap())
        .flat_map(|captures| 
            vec![captures.get(1).unwrap().as_str(), captures.get(2).unwrap().as_str()])
        .map(|group| group.split(',').collect::<Vec<&str>>())
        .map(|point_raw| 
            Point { x: i32::from_str(point_raw[0]).unwrap(), y: i32::from_str(point_raw[1]).unwrap() })
        .collect::<Vec<Point>>()
        .into_iter()
        .tuples()
        .map(|(p, q)| Line { p, q })
        .collect::<Vec<Line>>();

    // -- Part 1 --
    let point_frequency = line_segments.iter()
        .filter(|seg| seg.p.x == seg.q.x || seg.p.y == seg.q.y)
        .map(|seg| seg.to_points())
        .fold(HashMap::<Point, i32>::new(), |mut freqs, points| {
            points.into_iter()
                .for_each(|point| {
                    match freqs.contains_key(&point) {
                        true => {
                            let &current_frequency = freqs.get(&point).unwrap();
                            freqs.insert(point, current_frequency + 1);
                        }
                        false => {
                            freqs.insert(point, 1);
                        }
                    }
                });

            freqs
        });

    let num_intersections = point_frequency.keys()
        .filter(|key| point_frequency.get(key).unwrap() > &1)
        .count();

    println!("there are {} intersections", num_intersections);

    // -- Part 2 --
    let point_frequency = line_segments.iter()
        .map(|seg| seg.to_points())
        .fold(HashMap::<Point, i32>::new(), |mut freqs, points| {
            points.into_iter()
                .for_each(|point| {
                    match freqs.contains_key(&point) {
                        true => {
                            let &current_frequency = freqs.get(&point).unwrap();
                            freqs.insert(point, current_frequency + 1);
                        }
                        false => {
                            freqs.insert(point, 1);
                        }
                    }
                });

            freqs
        });

    let num_intersections = point_frequency.keys()
        .filter(|key| point_frequency.get(key).unwrap() > &1)
        .count();

    println!("there are {} intersections", num_intersections);
}
