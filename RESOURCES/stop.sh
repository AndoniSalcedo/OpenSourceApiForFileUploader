cd deploy/k8s
bash stop.sh
cd ../../

minikube ssh -- "sudo rm -rf /mnt/ssd/*"