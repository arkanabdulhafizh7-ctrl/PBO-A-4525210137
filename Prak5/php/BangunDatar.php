<?php
declare(strict_types=1);

abstract class BangunDatar
{
    public function __construct(private readonly string $nama) {}

    abstract public function luas(): float;
    abstract public function keliling(): float;

    public function getNama(): string { return $this->nama; }

    public function __toString(): string
    {
        return sprintf('%-12s luas=%10.2f  keliling=%10.2f',
            $this->nama, $this->luas(), $this->keliling());
    }
}

class Lingkaran extends BangunDatar
{
    public function __construct(private readonly float $jariJari)
    {
        parent::__construct('Lingkaran');
        if ($this->jariJari <= 0) {
            throw new InvalidArgumentException('Jari-jari lingkaran harus > 0.');
        }
    }

    public function luas(): float     { return M_PI * $this->jariJari * $this->jariJari; }
    public function keliling(): float { return 2 * M_PI * $this->jariJari; }

    public function getJariJari(): float { return $this->jariJari; }
}

class Persegi extends BangunDatar
{
    public function __construct(private readonly float $sisi)
    {
        parent::__construct('Persegi');
        if ($this->sisi <= 0) {
            throw new InvalidArgumentException('Sisi persegi harus > 0.');
        }
    }

    public function luas(): float     { return $this->sisi * $this->sisi; }
    public function keliling(): float { return 4 * $this->sisi; }
}

class Segitiga extends BangunDatar
{
    public function __construct(
        private readonly float $sisiA,
        private readonly float $sisiB,
        private readonly float $sisiC,
    ) {
        parent::__construct('Segitiga');
        if ($this->sisiA <= 0 || $this->sisiB <= 0 || $this->sisiC <= 0) {
            throw new InvalidArgumentException('Semua sisi segitiga harus > 0.');
        }
        if ($this->sisiA + $this->sisiB <= $this->sisiC || $this->sisiA + $this->sisiC <= $this->sisiB || $this->sisiB + $this->sisiC <= $this->sisiA) {
            throw new InvalidArgumentException('Ketiga sisi tidak membentuk segitiga.');
        }
    }

    public function luas(): float
    {
        $s = $this->keliling() / 2;
        return sqrt($s * ($s - $this->sisiA) * ($s - $this->sisiB) * ($s - $this->sisiC));
    }

    public function keliling(): float
    {
        return $this->sisiA + $this->sisiB + $this->sisiC;
    }
}

class Trapesium extends BangunDatar
{
    public function __construct(
        private readonly float $sisiAtas,
        private readonly float $sisiBawah,
        private readonly float $tinggi,
    ) {
        parent::__construct('Trapesium');
        if ($this->sisiAtas <= 0 || $this->sisiBawah <= 0 || $this->tinggi <= 0) {
            throw new InvalidArgumentException('Sisi dan tinggi trapesium harus > 0.');
        }
    }

    public function luas(): float
    {
        return (($this->sisiAtas + $this->sisiBawah) / 2) * $this->tinggi;
    }

    public function keliling(): float
    {
        $selisih = abs($this->sisiBawah - $this->sisiAtas) / 2;
        $sisiMiring = hypot($selisih, $this->tinggi);
        return $this->sisiAtas + $this->sisiBawah + (2 * $sisiMiring);
    }
}
