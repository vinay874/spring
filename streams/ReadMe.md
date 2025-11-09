# Java 8 Streams - Interview Preparation Guide

A comprehensive collection of Java 8 Stream API examples organized by difficulty level, designed to help you master streams for technical interviews.

## 📚 Table of Contents

1. [Introduction](#introduction)
2. [Java 8 Streams Fundamentals](#java-8-streams-fundamentals)
3. [Super Easy Level](#super-easy-level)
4. [Easy Level](#easy-level)
5. [Intermediate Level](#intermediate-level)
6. [Hard Level](#hard-level)
7. [Key Stream Operations Reference](#key-stream-operations-reference)
8. [Common Interview Patterns](#common-interview-patterns)
9. [Interview Tips](#interview-tips)
10. [Project Structure](#project-structure)

---

## Introduction

This repository contains practical examples of Java 8 Stream API operations, organized from basic to advanced levels. Each example demonstrates real-world scenarios you might encounter in technical interviews.

**Java Version:** 17  
**Build Tool:** Maven

---

## Java 8 Streams Fundamentals

### What are Streams?

Streams are a sequence of elements supporting sequential and parallel aggregate operations. They allow you to process collections of data in a declarative way, similar to SQL statements.

### Key Characteristics

- **Non-mutating**: Streams don't modify the source data
- **Lazy evaluation**: Operations are executed only when a terminal operation is called
- **Functional style**: Encourages declarative programming
- **Parallelizable**: Can process data in parallel for better performance

### Stream Pipeline Structure

```
Source → Intermediate Operations → Terminal Operation
```

**Example:**
```java
list.stream()                    // Source
    .filter(x -> x > 10)         // Intermediate operation
    .map(x -> x * 2)             // Intermediate operation
    .collect(Collectors.toList()) // Terminal operation
```

### Creating Streams

1. **From Collections:**
   ```java
   List<String> list = Arrays.asList("a", "b", "c");
   Stream<String> stream = list.stream();
   ```

2. **From Arrays:**
   ```java
   String[] array = {"a", "b", "c"};
   Stream<String> stream = Arrays.stream(array);
   ```

3. **Using Stream.of():**
   ```java
   Stream<Integer> stream = Stream.of(1, 2, 3, 4);
   ```

4. **Using Stream.generate():**
   ```java
   Stream<Double> randomStream = Stream.generate(Math::random).limit(100);
   ```

5. **From Primitives:**
   ```java
   IntStream intStream = IntStream.range(1, 10);
   ```

---

## Super Easy Level

### 1. CreateStreams
**Concept:** Different ways to create streams in Java

**Key Points:**
- `list.stream()` - Creates stream from List
- `Arrays.stream(array)` - Creates stream from array
- `Stream.of(...)` - Creates stream from individual elements
- `Stream.generate(supplier).limit(n)` - Generates infinite stream, limited to n elements

**Use Case:** Understanding stream creation is fundamental for all stream operations.

---

### 2. FilterEvenNumbers
**Concept:** Filtering elements based on a condition

**Problem:** Filter even numbers from a list

**Solution Pattern:**
```java
List<Integer> evenNumbers = numbers.stream()
    .filter(n -> n % 2 == 0)
    .toList();
```

**Key Points:**
- `filter(Predicate)` - Returns stream of elements matching the predicate
- Predicate: `n -> n % 2 == 0` checks if number is even
- `toList()` - Terminal operation that collects results into a list

---

### 3. SquareNumbers
**Concept:** Transforming elements using map operation

**Problem:** Square each number in a list

**Solution Pattern:**
```java
List<Integer> squared = numbers.stream()
    .map(n -> n * n)
    .toList();
```

**Key Points:**
- `map(Function)` - Transforms each element
- One-to-one transformation (one input → one output)

---

### 4. SquareEvenNumbers
**Concept:** Chaining multiple operations

**Problem:** Square only the even numbers

**Solution Pattern:**
```java
List<Integer> result = numbers.stream()
    .filter(n -> n % 2 == 0)  // First filter
    .map(n -> n * n)          // Then transform
    .toList();
```

**Key Points:**
- Operations are chained and executed lazily
- Order matters: filter first to reduce elements before mapping

---

### 5. SumOfElements
**Concept:** Reduction operations

**Problem:** Calculate sum and product of elements

**Solution Pattern:**
```java
// Sum
Integer sum = numbers.stream()
    .reduce(0, Integer::sum);

// Product
Integer product = numbers.stream()
    .reduce(1, (a, b) -> a * b);
```

**Key Points:**
- `reduce(identity, BinaryOperator)` - Combines elements
- Identity value: 0 for sum, 1 for product
- `Integer::sum` is a method reference equivalent to `(a, b) -> a + b`

---

### 6. SumOfEvenNumbers
**Concept:** Combining filter and reduce

**Problem:** Sum only even numbers

**Solution Pattern:**
```java
Integer sum = numbers.stream()
    .filter(n -> n % 2 == 0)
    .reduce(0, Integer::sum);
```

---

### 7. SumOfSquareOfEven
**Concept:** Complex chaining of operations

**Problem:** Sum the squares of even numbers

**Solution Pattern:**
```java
Integer sum = numbers.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * n)
    .reduce(0, Integer::sum);
```

**Key Points:**
- Demonstrates: filter → map → reduce pattern
- Common pattern in stream processing

---

### 8. MaxNumber
**Concept:** Finding maximum value

**Problem:** Find maximum number in a list

**Solution Pattern:**
```java
Optional<Integer> max = numbers.stream()
    .max(Integer::compareTo);
```

**Key Points:**
- `max(Comparator)` - Returns Optional
- `Integer::compareTo` - Natural ordering comparator
- Always returns Optional (empty if stream is empty)

---

### 9. FindFirstNumberGreaterThan
**Concept:** Short-circuit operations

**Problem:** Find first number greater than a threshold

**Solution Pattern:**
```java
Optional<Integer> result = numbers.stream()
    .filter(n -> n > threshold)
    .findFirst();
```

**Key Points:**
- `findFirst()` - Short-circuit terminal operation
- Stops processing once first match is found
- Returns Optional

---

### 10. CountGreaterNumbers
**Concept:** Counting elements

**Problem:** Count numbers greater than a threshold

**Solution Pattern:**
```java
long count = numbers.stream()
    .filter(n -> n > threshold)
    .count();
```

**Key Points:**
- `count()` - Terminal operation returning long
- Efficient for counting filtered elements

---

## Easy Level

### 1. FindAverage
**Concept:** Using specialized stream types for primitives

**Problem:** Calculate average of numbers

**Solution Pattern:**
```java
double average = numbers.stream()
    .mapToInt(Integer::intValue)  // Convert to IntStream
    .average()                     // Returns OptionalDouble
    .orElse(0.0);                  // Handle empty case
```

**Key Points:**
- `mapToInt()` - Converts to IntStream (specialized for primitives)
- `average()` - Returns OptionalDouble (not Optional<Double>)
- `orElse()` - Provides default value if Optional is empty
- More efficient than using regular Stream<Integer>

---

### 2. RemoveDuplicates
**Concept:** Removing duplicate elements

**Problem:** Remove duplicates from a list

**Solution Pattern:**
```java
List<Integer> unique = numbers.stream()
    .distinct()
    .toList();
```

**Key Points:**
- `distinct()` - Removes duplicates based on equals()
- Preserves order (first occurrence kept)
- More efficient than converting to Set and back

---

### 3. SecondHighestNumber
**Concept:** Sorting and skipping elements

**Problem:** Find second highest number

**Solution Pattern:**
```java
Optional<Integer> secondHighest = numbers.stream()
    .sorted(Comparator.reverseOrder())  // Sort descending
    .skip(1)                            // Skip first (highest)
    .findFirst();                       // Get second
```

**Key Points:**
- `sorted(Comparator)` - Sorts elements
- `Comparator.reverseOrder()` - Descending order
- `skip(n)` - Skips first n elements
- `findFirst()` - Gets first element after skip

---

### 4. AllPositiveExample
**Concept:** Checking if all elements match condition

**Problem:** Check if all numbers are positive

**Solution Pattern:**
```java
boolean allPositive = numbers.stream()
    .allMatch(n -> n > 0);
```

**Key Points:**
- `allMatch(Predicate)` - Returns true if ALL elements match
- Short-circuits on first false
- Returns true for empty stream

---

### 5. CountStartWithA
**Concept:** String filtering and counting

**Problem:** Count strings starting with 'A'

**Solution Pattern:**
```java
long count = strings.stream()
    .filter(s -> s.startsWith("A"))
    .count();
```

**Key Points:**
- String operations in streams
- `startsWith()` - String method for prefix checking
- Case-sensitive by default

---

### 6. FindFirstNonEmptyString
**Concept:** Finding first matching element

**Problem:** Find first non-empty string

**Solution Pattern:**
```java
Optional<String> first = strings.stream()
    .filter(s -> !s.isEmpty())
    .findFirst();
```

**Key Points:**
- `isEmpty()` - String method
- `findFirst()` with filter pattern

---

### 7. FlattenExample
**Concept:** Flattening nested collections

**Problem:** Flatten a list of lists into single list

**Solution Pattern:**
```java
List<Integer> flattened = listOfLists.stream()
    .flatMap(List::stream)
    .toList();
```

**Key Points:**
- `flatMap(Function)` - Flattens one-to-many relationships
- `List::stream` - Method reference to convert List to Stream
- Essential for nested collections

---

### 8. SortLists
**Concept:** Sorting collections

**Problem:** Sort a list of numbers

**Solution Pattern:**
```java
List<Integer> sorted = numbers.stream()
    .sorted()                    // Natural order
    .toList();

List<Integer> reverseSorted = numbers.stream()
    .sorted(Comparator.reverseOrder())  // Reverse order
    .toList();
```

**Key Points:**
- `sorted()` - Natural ordering
- `sorted(Comparator)` - Custom ordering
- Creates new collection (doesn't modify source)

---

### 9. NumberDivisibleBy
**Concept:** Filtering with multiple conditions

**Problem:** Find numbers divisible by a given number

**Solution Pattern:**
```java
List<Integer> divisible = numbers.stream()
    .filter(n -> n % divisor == 0)
    .toList();
```

---

### 10. StringJoiningExample
**Concept:** Joining strings

**Problem:** Join strings with delimiter

**Solution Pattern:**
```java
String result = strings.stream()
    .collect(Collectors.joining(", "));
```

**Key Points:**
- `Collectors.joining()` - Joins strings
- `Collectors.joining(delimiter)` - With delimiter
- `Collectors.joining(delimiter, prefix, suffix)` - With prefix/suffix

---

## Intermediate Level

### 1. AverageAgeExample
**Concept:** Working with custom objects and method references

**Problem:** Calculate average age from Person objects

**Solution Pattern:**
```java
double average = persons.stream()
    .mapToInt(Person::getAge)  // Method reference
    .average()
    .orElse(0.0);
```

**Key Points:**
- `Person::getAge` - Method reference (equivalent to `p -> p.getAge()`)
- Working with custom objects
- Using IntStream for primitive operations

---

### 2. CountOccurrence
**Concept:** Grouping and counting elements

**Problem:** Count occurrences of each element

**Solution Pattern:**
```java
Map<String, Long> frequency = items.stream()
    .collect(Collectors.groupingBy(
        Function.identity(),           // Group by element itself
        Collectors.counting()          // Count occurrences
    ));
```

**Key Points:**
- `Collectors.groupingBy()` - Groups elements
- `Function.identity()` - Returns element as-is
- `Collectors.counting()` - Counts elements in each group
- Returns Map<Element, Count>

---

### 3. GroupByDepartment
**Concept:** Grouping by object property

**Problem:** Group employees by department and calculate average salary

**Solution Pattern:**
```java
Map<String, Double> avgSalaryByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,                    // Group by department
        Collectors.averagingInt(Employee::getSalary) // Average salary
    ));
```

**Key Points:**
- `Collectors.groupingBy(classifier, downstream)` - Two-parameter version
- `Collectors.averagingInt()` - Calculates average of int values
- Method reference for getter: `Employee::getDepartment`

---

### 4. GroupByLength
**Concept:** Grouping by computed property

**Problem:** Group strings by their length

**Solution Pattern:**
```java
Map<Integer, List<String>> byLength = strings.stream()
    .collect(Collectors.groupingBy(String::length));
```

**Key Points:**
- Grouping by computed value (length)
- `String::length` - Method reference for string length

---

### 5. PartitionEvenOdd
**Concept:** Partitioning (special case of grouping with boolean key)

**Problem:** Partition numbers into even and odd

**Solution Pattern:**
```java
Map<Boolean, List<Integer>> partitions = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));
```

**Key Points:**
- `Collectors.partitioningBy()` - Always returns Map<Boolean, List>
- More efficient than groupingBy for boolean predicates
- Two groups: true (even) and false (odd)

---

### 6. SortEmployeeBySalary
**Concept:** Sorting custom objects

**Problem:** Sort employees by salary

**Solution Pattern:**
```java
List<Employee> sorted = employees.stream()
    .sorted(Comparator.comparing(Employee::getSalary))
    .toList();

// Descending order
List<Employee> reverseSorted = employees.stream()
    .sorted(Comparator.comparing(Employee::getSalary).reversed())
    .toList();
```

**Key Points:**
- `Comparator.comparing()` - Creates comparator from function
- `reversed()` - Reverses comparator order
- Can chain: `comparing(...).thenComparing(...)`

---

### 7. HighestPaidEmployee
**Concept:** Finding max/min with custom comparator

**Problem:** Find highest paid employee

**Solution Pattern:**
```java
Optional<Employee> highest = employees.stream()
    .max(Comparator.comparing(Employee::getSalary));
```

**Key Points:**
- `max(Comparator)` - Finds maximum element
- Returns Optional (empty if stream is empty)

---

### 8. HighestAvgSalaryDept
**Concept:** Complex aggregation and finding max

**Problem:** Find department with highest average salary

**Solution Pattern:**
```java
Optional<Map.Entry<String, Double>> result = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.averagingInt(Employee::getSalary)
    ))
    .entrySet()
    .stream()
    .max(Map.Entry.comparingByValue());
```

**Key Points:**
- Chaining stream operations on Map results
- `entrySet().stream()` - Convert Map to Stream
- `Map.Entry.comparingByValue()` - Comparator for Map entries

---

### 9. DepartmentEmployeeCount
**Concept:** Counting grouped elements

**Problem:** Count employees per department

**Solution Pattern:**
```java
Map<String, Long> countByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.counting()
    ));
```

**Key Points:**
- `Collectors.counting()` - Counts elements in each group

---

### 10. MostFrequentChar
**Concept:** Character frequency analysis

**Problem:** Find most frequent character in a string

**Solution Pattern:**
```java
Optional<Map.Entry<Character, Long>> mostFrequent = str.chars()
    .mapToObj(c -> (char) c)
    .collect(Collectors.groupingBy(
        Function.identity(),
        Collectors.counting()
    ))
    .entrySet()
    .stream()
    .max(Map.Entry.comparingByValue());
```

**Key Points:**
- `str.chars()` - Returns IntStream of character codes
- `mapToObj(c -> (char) c)` - Convert int to char
- Character frequency analysis pattern

---

### 11. FirstNonRepeatingCharStream
**Concept:** Finding first element with specific property

**Problem:** Find first non-repeating character

**Solution Pattern:**
```java
Optional<Map.Entry<Character, Long>> first = str.chars()
    .mapToObj(c -> (char) c)
    .collect(Collectors.groupingBy(
        Function.identity(),
        LinkedHashMap::new,           // Preserve insertion order
        Collectors.counting()
    ))
    .entrySet()
    .stream()
    .filter(entry -> entry.getValue() == 1)
    .findFirst();
```

**Key Points:**
- `LinkedHashMap::new` - Preserves insertion order
- Three-parameter groupingBy: `(classifier, mapFactory, downstream)`
- Filtering Map entries based on value

---

## Hard Level

### 1. Top3FrequentWords
**Concept:** Complex frequency analysis with sorting

**Problem:** Find top 3 most frequent words

**Solution Pattern:**
```java
List<Map.Entry<String, Long>> sorted = Arrays.stream(paragraph
        .toLowerCase()
        .split(" "))
    .collect(Collectors.groupingBy(
        Function.identity(),
        Collectors.counting()
    ))
    .entrySet()
    .stream()
    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
    .toList();

List<Map.Entry<String, Long>> top3 = sorted.stream()
    .limit(3)
    .toList();
```

**Key Points:**
- Sorting Map entries by value in descending order
- `comparingByValue().reversed()` - Sort by value, descending
- `limit(n)` - Get first n elements
- Multi-step processing pattern

---

### 2. BigramFrequency
**Concept:** Working with adjacent elements (sliding window)

**Problem:** Find bigrams (word pairs) that appear at least twice

**Solution Pattern:**
```java
String[] words = sentence.toLowerCase().split(" ");

List<Map.Entry<String, Long>> bigrams = IntStream.range(0, words.length - 1)
    .mapToObj(i -> words[i] + " " + words[i + 1])  // Create bigrams
    .collect(Collectors.groupingBy(
        Function.identity(),
        Collectors.counting()
    ))
    .entrySet()
    .stream()
    .filter(e -> e.getValue() >= 2)  // At least 2 occurrences
    .toList();
```

**Key Points:**
- `IntStream.range()` - Generate indices
- Creating pairs from adjacent elements
- Sliding window pattern for n-grams

---

### 3. SlidingWindowAverage
**Concept:** Calculating moving averages

**Problem:** Calculate average for each sliding window of size k

**Solution Pattern:**
```java
List<Double> averages = IntStream.range(0, input.size() - window + 1)
    .mapToObj(i -> input.subList(i, i + window))  // Get window
    .map(window -> window.stream()
        .mapToInt(Integer::intValue)
        .average()
        .orElse(0))
    .toList();
```

**Key Points:**
- Sliding window algorithm
- `subList()` - Get sublist for window
- Nested stream operations
- Common in time-series analysis

---

### 4. EmployeeWorked
**Concept:** Complex filtering on grouped data

**Problem:** Find employees who worked in at least 3 departments

**Solution Pattern:**
```java
List<String> employees = workRecords.stream()
    .collect(Collectors.groupingBy(
        WorkRecord::getName,
        Collectors.counting()
    ))
    .entrySet()
    .stream()
    .filter(e -> e.getValue() >= 3)  // At least 3 departments
    .map(Map.Entry::getKey)
    .toList();
```

**Key Points:**
- Grouping and counting
- Filtering based on count
- Extracting keys from filtered entries

---

### 5. HighestSpendDay
**Concept:** Date-based grouping and aggregation

**Problem:** Find day with highest total spending

**Solution Pattern:**
```java
Optional<Map.Entry<LocalDate, Double>> result = transactions.stream()
    .collect(Collectors.groupingBy(
        Transaction::getDate,
        Collectors.summingDouble(Transaction::getAmount)
    ))
    .entrySet()
    .stream()
    .max(Map.Entry.comparingByValue());
```

**Key Points:**
- Grouping by date
- `Collectors.summingDouble()` - Sum double values
- Finding max from grouped results
- Working with LocalDate

---

### 6. SalaryRangeGrouping
**Concept:** Grouping into custom ranges

**Problem:** Group employees by salary ranges

**Solution Pattern:**
```java
Map<SalaryRange, List<Employee>> grouped = employees.stream()
    .collect(Collectors.groupingBy(employee -> {
        int salary = employee.getSalary();
        if (salary < 50000) return SalaryRange.LOW;
        else if (salary < 100000) return SalaryRange.MEDIUM;
        else return SalaryRange.HIGH;
    }));
```

**Key Points:**
- Custom grouping logic
- Categorizing into ranges
- Using enums for categories

---

### 7. GroupCharacters
**Concept:** Categorizing characters

**Problem:** Group characters by category (vowel, consonant, digit, etc.)

**Solution Pattern:**
```java
Map<CharCategory, List<Character>> grouped = str.chars()
    .mapToObj(c -> (char) c)
    .collect(Collectors.groupingBy(c -> {
        if (isVowel(c)) return CharCategory.VOWEL;
        else if (Character.isDigit(c)) return CharCategory.DIGIT;
        else if (Character.isLetter(c)) return CharCategory.CONSONANT;
        else return CharCategory.OTHER;
    }));
```

**Key Points:**
- Character classification
- Using helper methods in grouping
- Enum-based categorization

---

### 8. MostCommonFirstLetter
**Concept:** String analysis and grouping

**Problem:** Find most common first letter of words

**Solution Pattern:**
```java
Optional<Map.Entry<Character, Long>> result = Arrays.stream(text.split(" "))
    .map(word -> word.charAt(0))
    .collect(Collectors.groupingBy(
        Function.identity(),
        Collectors.counting()
    ))
    .entrySet()
    .stream()
    .max(Map.Entry.comparingByValue());
```

**Key Points:**
- Extracting first character
- Grouping and counting
- Finding maximum

---

### 9. LongestWordFinder
**Concept:** Finding element with maximum property

**Problem:** Find longest word in a sentence

**Solution Pattern:**
```java
Optional<String> longest = Arrays.stream(sentence.split(" "))
    .max(Comparator.comparing(String::length));
```

**Key Points:**
- `Comparator.comparing(String::length)` - Compare by length
- Simple max operation with custom comparator

---

### 10. ReverseWorksStream
**Concept:** Reversing order using streams

**Problem:** Reverse words in a sentence

**Solution Pattern:**
```java
String reversed = Arrays.stream(sentence.split(" "))
    .collect(Collectors.collectingAndThen(
        Collectors.toList(),
        list -> {
            Collections.reverse(list);
            return list;
        }
    ))
    .stream()
    .collect(Collectors.joining(" "));
```

**Key Points:**
- `Collectors.collectingAndThen()` - Apply function after collection
- Reversing list
- Rejoining strings

---

## Key Stream Operations Reference

### Intermediate Operations (Return Stream)

| Operation | Description | Example |
|-----------|-------------|---------|
| `filter(Predicate)` | Filters elements | `.filter(x -> x > 10)` |
| `map(Function)` | Transforms elements | `.map(x -> x * 2)` |
| `flatMap(Function)` | Flattens nested streams | `.flatMap(List::stream)` |
| `distinct()` | Removes duplicates | `.distinct()` |
| `sorted()` | Sorts elements | `.sorted()` |
| `sorted(Comparator)` | Sorts with comparator | `.sorted(Comparator.reverseOrder())` |
| `peek(Consumer)` | Performs side effect | `.peek(System.out::println)` |
| `limit(long)` | Limits size | `.limit(10)` |
| `skip(long)` | Skips elements | `.skip(5)` |
| `mapToInt()` | Converts to IntStream | `.mapToInt(Integer::intValue)` |
| `mapToDouble()` | Converts to DoubleStream | `.mapToDouble(Double::doubleValue)` |

### Terminal Operations (Return Result)

| Operation | Description | Return Type |
|-----------|-------------|-------------|
| `collect(Collector)` | Collects to collection | Collection |
| `toList()` | Collects to list | List |
| `forEach(Consumer)` | Performs action | void |
| `reduce()` | Reduces to single value | Optional/T |
| `count()` | Counts elements | long |
| `anyMatch(Predicate)` | Checks if any matches | boolean |
| `allMatch(Predicate)` | Checks if all match | boolean |
| `noneMatch(Predicate)` | Checks if none match | boolean |
| `findFirst()` | Finds first element | Optional |
| `findAny()` | Finds any element | Optional |
| `min(Comparator)` | Finds minimum | Optional |
| `max(Comparator)` | Finds maximum | Optional |
| `sum()` | Sums elements (IntStream) | int |
| `average()` | Averages elements | OptionalDouble |

### Common Collectors

| Collector | Description | Example |
|-----------|-------------|---------|
| `toList()` | Collects to list | `Collectors.toList()` |
| `toSet()` | Collects to set | `Collectors.toSet()` |
| `joining()` | Joins strings | `Collectors.joining(", ")` |
| `groupingBy()` | Groups elements | `Collectors.groupingBy(Function)` |
| `partitioningBy()` | Partitions by predicate | `Collectors.partitioningBy(Predicate)` |
| `counting()` | Counts elements | `Collectors.counting()` |
| `summingInt()` | Sums int values | `Collectors.summingInt(Function)` |
| `averagingInt()` | Averages int values | `Collectors.averagingInt(Function)` |
| `maxBy()` | Finds max | `Collectors.maxBy(Comparator)` |
| `minBy()` | Finds min | `Collectors.minBy(Comparator)` |

---

## Common Interview Patterns

### Pattern 1: Filter → Map → Collect
```java
List<Integer> result = numbers.stream()
    .filter(x -> x > 10)
    .map(x -> x * 2)
    .toList();
```

### Pattern 2: Group and Count
```java
Map<String, Long> frequency = items.stream()
    .collect(Collectors.groupingBy(
        Function.identity(),
        Collectors.counting()
    ));
```

### Pattern 3: Group and Aggregate
```java
Map<String, Double> avgByGroup = items.stream()
    .collect(Collectors.groupingBy(
        Item::getCategory,
        Collectors.averagingDouble(Item::getPrice)
    ));
```

### Pattern 4: Find Max/Min with Custom Comparator
```java
Optional<Employee> highest = employees.stream()
    .max(Comparator.comparing(Employee::getSalary));
```

### Pattern 5: Flatten Nested Collections
```java
List<Integer> flat = listOfLists.stream()
    .flatMap(List::stream)
    .toList();
```

### Pattern 6: Sliding Window
```java
List<List<T>> windows = IntStream.range(0, list.size() - k + 1)
    .mapToObj(i -> list.subList(i, i + k))
    .toList();
```

### Pattern 7: Frequency Analysis
```java
Map<T, Long> frequency = items.stream()
    .collect(Collectors.groupingBy(
        Function.identity(),
        Collectors.counting()
    ));
```

---

## Interview Tips

### 1. **Understand Lazy Evaluation**
- Intermediate operations are lazy (not executed until terminal operation)
- This allows optimization and short-circuiting

### 2. **Know When to Use Specialized Streams**
- Use `IntStream`, `LongStream`, `DoubleStream` for primitives
- More efficient and provides additional methods (sum, average, etc.)

### 3. **Method References vs Lambda**
- `String::length` is equivalent to `s -> s.length()`
- Method references are often more readable
- Use when lambda just calls a method

### 4. **Optional Handling**
- Many terminal operations return Optional
- Always handle empty case: `.orElse()`, `.orElseGet()`, `.orElseThrow()`

### 5. **Collector Patterns**
- `groupingBy()` - Most common for grouping
- `partitioningBy()` - Use for boolean predicates
- `collectingAndThen()` - Apply transformation after collection

### 6. **Performance Considerations**
- Filter before map (reduces elements to process)
- Use parallel streams for large datasets: `.parallelStream()`
- Be aware of stateful operations (sorted, distinct) - can be expensive

### 7. **Common Mistakes to Avoid**
- Forgetting terminal operation (stream won't execute)
- Modifying source collection during stream processing
- Using streams for simple operations that could be done with loops
- Not handling Optional properly

### 8. **Interview Questions to Prepare**
- Explain difference between `map()` and `flatMap()`
- When to use `findFirst()` vs `findAny()`
- Difference between `reduce()` and `collect()`
- How does `distinct()` work?
- Explain stream pipeline execution order

---

## Project Structure

```
streams/
├── src/
│   └── main/
│       └── java/
│           └── org/
│               └── example/
│                   ├── Main.java
│                   ├── supereasy/        # Basic stream operations
│                   │   ├── CreateStreams.java
│                   │   ├── FilterEvenNumbers.java
│                   │   ├── SquareNumbers.java
│                   │   └── ...
│                   ├── easy/             # Simple transformations
│                   │   ├── FindAverage.java
│                   │   ├── RemoveDuplicates.java
│                   │   ├── FlattenExample.java
│                   │   └── ...
│                   ├── intermediate/     # Grouping and aggregation
│                   │   ├── dto/
│                   │   │   ├── Employee.java
│                   │   │   └── Person.java
│                   │   ├── GroupByDepartment.java
│                   │   ├── CountOccurrence.java
│                   │   └── ...
│                   └── hard/             # Complex scenarios
│                       ├── dto/
│                       │   ├── Transaction.java
│                       │   ├── WorkRecord.java
│                       │   └── ...
│                       ├── Top3FrequentWords.java
│                       ├── SlidingWindowAverage.java
│                       └── ...
└── pom.xml
```

### Difficulty Levels Explained

- **Super Easy**: Basic stream creation, filtering, mapping, and simple reductions
- **Easy**: Working with primitives, sorting, flattening, and string operations
- **Intermediate**: Grouping, partitioning, custom objects, and aggregations
- **Hard**: Complex multi-step operations, sliding windows, frequency analysis, and advanced patterns

---

## Running the Examples

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Compile and Run
```bash
# Compile
mvn compile

# Run a specific example
cd target/classes
java org.example.supereasy.CreateStreams
```

### Using IDE
- Open project in IntelliJ IDEA or Eclipse
- Navigate to any example class
- Run the `main()` method

---

## Additional Resources

### Key Concepts to Master
1. **Stream Pipeline**: Source → Intermediate Ops → Terminal Op
2. **Lazy Evaluation**: Operations execute only when terminal op is called
3. **Method References**: `Class::method` syntax
4. **Optional**: Handling null-safe operations
5. **Collectors**: Powerful collection operations
6. **Primitive Streams**: IntStream, LongStream, DoubleStream
7. **Parallel Streams**: `.parallelStream()` for concurrent processing

### Practice Recommendations
1. Start with super easy examples to understand basics
2. Practice writing solutions from scratch
3. Understand when to use each operation
4. Practice explaining your code out loud (as in interviews)
5. Try solving similar problems with different approaches
6. Focus on readability and efficiency

---

## Summary

This repository provides a comprehensive guide to Java 8 Streams API with examples ranging from basic operations to complex real-world scenarios. Each example demonstrates practical patterns you'll encounter in technical interviews.

**Key Takeaways:**
- Streams provide a declarative way to process collections
- Understanding filter, map, reduce, and collect is essential
- Grouping and partitioning are powerful for data analysis
- Method references improve code readability
- Always handle Optional properly
- Practice explaining stream operations clearly

Good luck with your interviews! 🚀
