namespace java cpnode
namespace py cpnode
exception InvalidInput {
   1: i32 what,
   2: string why
}
service ComputeNodeService{
     bool imgprocess(1: string filepath),
     bool canny_edge_detect(1: string filepath),
}
