#include "collection_utils.h"

#include <iostream>
#include <unordered_map>
#include <vector>

namespace advent_of_code {

void PopulateFrequencyMap(const std::vector<int>& numbers, std::unordered_map<int, int>& occurrences) {
	if (numbers.empty()) {
		std::cout << "Unable to populate frequency map. Input numbers vector empty." << std::endl;
		return;
	}
	for (int n : numbers) {
		if (occurrences.find(n) == occurrences.end()) {
			occurrences.insert(std::make_pair(n, 1));
		}
		else {
			occurrences.insert_or_assign(n, occurrences.at(n) + 1);
		}
	}
}

}