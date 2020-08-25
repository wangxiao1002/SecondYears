


tables=[
        [
            {
                "date": "2020-06-17",
                "num": 1
            },
            {
                "date": "2020-06-18",
                "num": 1,
                "rate": 100.0
            }
        ],
        [
            {
                "date": "2020-06-16",
                "num": 0
            },
            {
                "date": "2020-06-17",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-18",
                "num": 0,
                "rate": 0.0
            }
        ],
        [
            {
                "date": "2020-06-15",
                "num": 0
            },
            {
                "date": "2020-06-16",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-17",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-18",
                "num": 0,
                "rate": 0.0
            }
        ],
        [
            {
                "date": "2020-06-14",
                "num": 0
            },
            {
                "date": "2020-06-15",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-16",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-17",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-18",
                "num": 0,
                "rate": 0.0
            }
        ],
        [
            {
                "date": "2020-06-13",
                "num": 0
            },
            {
                "date": "2020-06-14",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-15",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-16",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-17",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-18",
                "num": 0,
                "rate": 0.0
            }
        ],
        [
            {
                "date": "2020-06-12",
                "num": 0
            },
            {
                "date": "2020-06-13",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-14",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-15",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-16",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-17",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-18",
                "num": 0,
                "rate": 0.0
            }
        ],
        [
            {
                "date": "2020-06-11",
                "num": 0
            },
            {
                "date": "2020-06-12",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-13",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-14",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-15",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-16",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-17",
                "num": 0,
                "rate": 0.0
            },
            {
                "date": "2020-06-18",
                "num": 0,
                "rate": 0.0
            }
        ]
    ]

# for item in tables:
#     print(item[0]['num'])
agree=[]
j = sum([item[0]['num'] for item in tables])

i = 1
while i < 7:
    tot = 0
    for item in tables:
        tot += item[i]['num'] if len(item) - 1 >= i else 0

    i += 1
    agree.append(round(tot * 100.0 / j if j else 0.0, 2))


print(agree)