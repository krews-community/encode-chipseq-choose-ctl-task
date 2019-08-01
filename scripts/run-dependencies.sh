#!/bin/bash

set -e

# cd to project root directory
cd "$(dirname "$(dirname "$0")")"

docker build --target base -t genomealmanac/chipseq-choose-ctl-base .

docker run --name chipseq-choose-ctl-base --rm -i -t -d \
    -v /tmp/chipseq-test:/tmp/chipseq-test \
    genomealmanac/chipseq-choose-ctl-base /bin/sh