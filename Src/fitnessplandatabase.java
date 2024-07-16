fitness_plans.db
AMRIL(24000254),KAGENDRAN(24000582),AMIR(24000387),ADAM(24000263),AKID(24000336)
CREATE TABLE FitnessPlans (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        age INTEGER NOT NULL,
        weight REAL NOT NULL,
        height REAL NOT NULL,
        bmi REAL NOT NULL,
        objectives TEXT NOT NULL,
        startDate TEXT NOT NULL,
        endDate TEXT NOT NULL
);