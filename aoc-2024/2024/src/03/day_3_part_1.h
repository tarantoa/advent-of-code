#pragma once

#include <regex>

namespace advent_of_code {
namespace Day3_Part1 {

void CollectValidInstructions(const std::string& instruction, std::vector<std::string>& valid_instructions);
int EvaluateInstructions(const std::vector<std::string>& instructions);

int main();

const std::regex valid_instruction_("(mul\\(\\d+,\\d+\\))");
const std::regex numbers_("(\\d+)");

}
}