from dataclasses import dataclass


@dataclass
class Result:
    value: str

    def __repr__(self):
        return f"{self.value}"

