#pragma once

#include <string>
#include <vector>

namespace advent_of_code {
namespace FileUtils {

void ReadFileToVectorPair(const std::string& filepath, std::vector<int>& first, std::vector<int>& second);
void ReadFileTo2DVector(const std::string& filepath, std::vector<std::vector<int>>& reports);
void ReadFileToStringVector(const std::string& filepath, std::vector<std::string>& lines);

}
}