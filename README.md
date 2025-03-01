# TOYRISC Processor Simulator

## Project Overview
This **TOYRISC Processor Simulator** is a simplified **RISC (Reduced Instruction Set Computing) processor** simulation project developed as part of my **Computer Architecture coursework at IIT Dharwad**. The goal was to understand and implement key architectural concepts such as **instruction execution, single-cycle processing, and pipelining**. 

The simulator is entirely built in **Java**, ensuring platform independence and efficient execution using the **Java Virtual Machine (JVM)**.

---
## Key Components & Features
### Assembler
- Converts **ToyRISC assembly language instructions** into machine code.
- Supports fundamental instructions, including:
  - **Arithmetic operations** (ADD, SUB, etc.)
  - **Memory access** (LOAD, STORE)
  - **Control flow** (JUMP, BRANCH)

### Single-Cycle Processor Simulator
- Implements a **single-cycle execution model**, where **one instruction executes per clock cycle**.
- Simulates key stages:
  - **Instruction Fetch (IF)**
  - **Instruction Decode (ID)**
  - **Execution (EX)**
  - **Memory Access (MEM)**
  - **Write-back (WB)**
- Helps in understanding fundamental processor design and execution flow.

### Pipelined Processor Simulator
- Implements a **pipelined execution model** to simulate concurrent instruction execution.
- Divides instruction processing into multiple stages for **higher throughput**.
- Handles **pipeline hazards** such as:
  - **Data Hazards** (via forwarding and stalling)
  - **Control Hazards** (using branch prediction and stalling mechanisms)

---
## 🛠 Technical Implementation
- Developed in **Java** using **Object-Oriented Programming (OOP)** principles for modular and reusable components.
- Key integrated components:
  - **Instruction Set Architecture (ISA)**
  - **Register File**
  - **Arithmetic Logic Unit (ALU)**
  - **Memory Unit**
- Supports a variety of **ToyRISC instructions**, including:
  - **Arithmetic**: ADD, SUB
  - **Logical**: AND, OR
  - **Memory Operations**: LOAD, STORE
  - **Control Flow**: JUMP, BRANCH


