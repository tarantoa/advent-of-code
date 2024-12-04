#include "day_3_part_1.h"

#include <cassert>
#include <iostream>
#include <regex>
#include <string>
#include <vector>

#include "file_utils.h"

namespace advent_of_code {
namespace Day3_Part1 {

void CollectValidInstructions(const std::string& instruction, std::vector<std::string>& valid_instructions) {
	auto instruction_begin = std::sregex_iterator(instruction.begin(), instruction.end(), valid_instruction_);
	auto instruction_end = std::sregex_iterator();
	for (std::sregex_iterator it = instruction_begin; it != instruction_end; it++) {
		valid_instructions.push_back((*it).str());
	}
}

int EvaluateInstructions(const std::vector<std::string>& instructions) {
	int sum = 0;
	for (const auto& instruction : instructions) {
		auto instruction_args = std::sregex_iterator(instruction.begin(), instruction.end(), numbers_);
		int first = std::stoi((*instruction_args).str());
		instruction_args++;
		int second = std::stoi((*instruction_args).str());
		sum += first * second;
	}
	return sum;
}

int main() {
	std::vector<std::string> instructions;
	std::string filepath("resources/03/test.txt");

	FileUtils::ReadFileToStringVector(filepath, instructions);

	assert(!instructions.empty());

	long instruction_sum = 0;
	for (const std::string& instruction : instructions) {
		std::vector<std::string> valid_instructions;
		CollectValidInstructions(instruction, valid_instructions);
		instruction_sum += EvaluateInstructions(valid_instructions);
	}

	// Print result.
	std::cout << "Sum of uncorrupted operations: " << instruction_sum << std::endl;

	return 0;
}

}
}