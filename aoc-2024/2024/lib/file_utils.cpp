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

void ReadFileTo2DVector(const std::string& filepath, std::vector<std::vector<int>>& reports) {
    if (filepath.empty()) {
        return;
    }
    std::ifstream input_file(filepath);
    if (!input_file.good()) {
        std::cout << "Unable to read input file \"" << filepath << "\"." << std::endl;
    }
    std::regex numbers("(\\d+)");
    std::string current_line;
    while (std::getline(input_file, current_line)) {
        auto line_numbers_begin = std::sregex_iterator(current_line.begin(), current_line.end(), numbers);
        auto line_numbers_end = std::sregex_iterator();
        std::vector<int> report;
        for (std::sregex_iterator it = line_numbers_begin; it != line_numbers_end; it++) {
            report.push_back(std::stoi((*it).str()));
        }
        reports.push_back(std::move(report));
    }
}

void ReadFileToStringVector(const std::string& filepath, std::vector<std::string>& lines) {
    if (filepath.empty()) {
        return;
    }
    std::ifstream input_file(filepath);
    if (!input_file.good()) {
        std::cout << "Unable to read input file \"" << filepath << "\"." << std::endl;
    }
    std::string current_line;
    while (std::getline(input_file, current_line)) {
        lines.push_back(current_line);
    }
}

}
}