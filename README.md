# Sales Invoices Generator (SIG)
A Java-based desktop application for creating, managing, and saving sales invoices.
---

## Table of Contents

- [Overview](#overview)  
- [Features](#features)  
- [Project Structure](#project-structure)  
- [Prerequisites](#prerequisites)  
- [How to Build and Run](#how-to-build-and-run)  
- [Usage](#usage)  

---

## Overview

The **Sales Invoices Generator (SIG)** is a Java project designed to simplify the generation and management of sales invoices. It provides functionality to create invoices, save and load invoice data, and maintain a history of invoices. The UI is built using Java (likely Swing) and the project is organized using standard Java project structure.  

---

## Features

- Create new sales invoices with invoice number, date, customer info, and line-items.  
- Delete existing invoices.  
- Save invoices / invoice data (persisting to files).  
- Load previously saved invoices.  
- Invoice history management (view past invoices).  

---

## Project Structure

Here's a high-level view of how the project is structured:

├── build/ # Compiled classes / build artifacts

├── nbproject/ # NetBeans project files

├── src/ # Source code

│ └── ... # Packages & Java classes

├── build.xml # Build script (Ant)

├── manifest.mf # Manifest for runnable jar / packaging

└── other config / resource files as needed

---

## Prerequisites

To build and run this project you’ll need:
- Java Development Kit (JDK) — version 8 or later (exact version depends on source)  
- Apache Ant (since there is a `build.xml`) or an IDE that supports Ant builds  
- NetBeans (optional, as the project is set up for NetBeans), but any Java IDE should work  

---

## How to Build and Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/Toqa-Adel/Sales-Invoices-Generator-
   cd Sales-Invoices-Generator-

2. **Build the project**
If using Ant:
ant

This should compile the .java files and place class files under build/.
If using an IDE like NetBeans:
- Open the project
- Let the IDE handle dependencies/build
- Run the main class

3. **Run the application**
From the IDE: run the main entry point (e.g. a class with public static void main)
Or from the compiled jar or classes folder if packaged.

4. **Usage**
- On startup, the application may allow loading of existing invoice data.
- The user can create a new invoice, filling in required details (invoice number, date, customer name, etc.).
- Line items can be added to the invoice (product, quantity, price).
- The invoice can be saved; saved files can be reloaded later.
- Existing invoices can be viewed, modified, or deleted.
