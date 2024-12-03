#include "day_2_part_1.h"

#include <iostream>
#include <cassert>
#include <string>
#include <vector>

#include "file_utils.h"

namespace advent_of_code {
namespace Day2_Part1 {

// Returns true if the given vector contains numbers that are only increasing
// or only decreasing.
bool IsUnidirectionalInterval(std::vector<int>& numbers) {
	if (numbers.empty() || numbers.size() == 1) {
		return true;
	}
	bool is_decreasing = numbers[0] > numbers[1];
	for (int i = 1; i < numbers.size(); i++) {
		if (is_decreasing && numbers[i] > numbers[i - 1]) {
			return false;
		}
		if (!is_decreasing && numbers[i] < numbers[i - 1]) {
			return false;
		}
	}
	return true;
}
// Returns true if the given vector contains elements that are greater than 0
// and less than or equal to max_element_difference from adjacent elements.
bool HasMaxElementDifferenceOfN(std::vector<int>& numbers, int max_element_difference) {
	if (numbers.empty() || numbers.size() == 1) {
		return true;
	}
	for (int i = 1; i < numbers.size(); i++) {
		int element_difference = abs(numbers[i] - numbers[i - 1]);
		if (element_difference < 1 || element_difference > max_element_difference) {
			return false;
		}
	}
	return true;
}

int main() {
	std::vector<std::vector<int>> reports;
	std::string filename("resources/02/part_1.txt");

	// Generate list of reports from input file.
	FileUtils::ReadFileTo2DVector(filename, reports);

	assert(!reports.empty());

	// Work for Part 1:
	// We need to count the number of reports that meet BOTH of:
	//	1. Entire report is either only ascending or only descending.
	//	2. The difference between each element in the report is always 1 or 2.
	int safe_reports = 0;

	for (auto report : reports) {
		if (IsUnidirectionalInterval(report) && HasMaxElementDifferenceOfN(report, 3)) {
			safe_reports++;
		}
	}

	// Print result.
	std::cout << "Number of total reports: " << reports.size() << std::endl;
	std::cout << "Number of safe reports: " << safe_reports << std::endl;

	return 0;
}

}
}