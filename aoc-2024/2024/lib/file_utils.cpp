#include "file_utils.h"

#include <iostream>
#include <fstream>
#include <regex>
#include <string>
#include <vector>

namespace advent_of_code {
namespace FileUtils {

void ReadFileToVectorPair(const std::string& filepath, std::vector<int>& first, std::vector<int>& second) {
    if (filepath.empty()) {
        return;
    }
    std::ifstream input_file(filepath);
    if (!input_file.good()) {
        std::cout << "Unable to read input file \"" << filepath << "\"." << std::endl;
        return;
    }
    std::regex expression("(\\d+)");
    std::string current_line;
    while (std::getline(input_file, current_line)) {
        auto line_numbers = std::sregex_iterator(current_line.begin(), current_line.end(), expression);
        first.push_back(std::stoi((*line_numbers).str()));
        line_numbers++;
        second.push_back(std::stoi((*line_numbers).str()));
    }
}

}
}