# NVM
# Custom Virtual Machine (VM)

A learning-focused, stack-based Virtual Machine implemented from scratch to understand how low-level execution models work.  
The VM interprets custom bytecode and executes basic arithmetic operations.

This project is inspired by studying how runtimes like the JVM execute bytecode, with a simplified design for educational purposes.

---

## Project Goals

- Understand how virtual machines execute instructions
- Learn stack-based execution models
- Design and interpret custom bytecode
- Build core runtime components step by step

---

## Current Features

- Stack-based execution model
- Custom bytecode format
- Instruction pointer for sequential execution
- Arithmetic operations:
  - Addition
  - Subtraction
  - Multiplication
  - Division
- Runtime operand stack management
- Bytecode interpretation loop

---

## Architecture Overview

- **VM** – Core execution engine
- **Chunk / Bytecode** – Stores instructions and constants
- **Opcode** – Defines instruction set
- **Stack** – Used for operand evaluation
- **Main** – Entry point for execution

---

