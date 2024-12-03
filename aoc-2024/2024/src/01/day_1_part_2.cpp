#include "day_1_part_2.h"

#include <cassert>
#include <iostream>
#include <string>
#include <unordered_map>
#include <utility>
#include <vector>

#include "collection_utils.h"
#include "file_utils.h"

namespace advent_of_code {
namespace Day1_Part2 {

int main() {
	std::vector<int> first;
	std::vector<int> second;
	std::string filename("resources/01/test.txt");

	// Populate vectors from file contents.
	FileUtils::ReadFileToVectorPair(filename, first, second);

	// Verify vector contents.
	assert(!first.empty());
	assert(!second.empty());

	// Work for Part 2:
	// We need to first figure out the number of occurrences for each value in
	// the second list and then we can construct the sum of each entry in the
	// first list multiplied by the number of times it occurs in the second.
	std::unordered_map<int, int> occurrences;
	PopulateFrequencyMap(second, occurrences);

	// Verify frequency map was populated.
	assert(!occurrences.empty());

	long similarity_score = 0;
	for (int n : first) {
		if (occurrences.find(n) == occurrences.end()) {
			continue;
		}
		similarity_score += n * occurrences[n];
	}

	// Print result.
	std::cout << "Similarity score: " << similarity_score << std::endl;
	return 0;
}

}
}