#include "day_1_part_1.h"

#include <algorithm>
#include <iostream>
#include <vector>

#include "file_utils.h"

namespace advent_of_code {
namespace Day1_Part1 {

int main() {
    std::vector<int> first;
    std::vector<int> second;
    std::string filename("resources/01/part_1.txt");

    // Populate vectors from file contents.
    FileUtils::ReadFileToVectorPair(filename, first, second);

    // Verify vector contents.
    if (first.empty()) {
        std::cout << "First vector empty! Terminating execution." << std::endl;
        return 1;
    }
    if (second.empty()) {
        std::cout << "Second vector empty! Terminating execution." << std::endl;
        return 1;
    }

    // Sort vectors for comparison.
    std::sort(first.begin(), first.end());
    std::sort(second.begin(), second.end());

    // Work for Part 1:
    // We simply need to sum the differences between individual vector items
    // in both arrays.
    long total_distance = 0;
    auto first_it = first.begin();
    auto second_it = second.begin();
    while (first_it != first.end() && second_it != second.end()) {
        long current_distance = abs((*second_it) - (*first_it));
        total_distance += current_distance;
        first_it++;
        second_it++;
    }
    
    // Print result.
    std::cout << "Total distance between vectors: " << total_distance << std::endl;

    return 0;
}

}
}