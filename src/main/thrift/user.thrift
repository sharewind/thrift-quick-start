// usage
// for java:
// cd ${project_path}
// thrift -gen java -out ./src/main/java ./src/main/thrift/user.thrift

namespace java com.cjf.practice
namespace py cloudatlas

const string VERSION = "1.0.0"

struct UserProfile {
    1: i32 uid,
    2: string name,
    3: string blurb
}

service UserStorage {
    void store(1: UserProfile user),
    UserProfile retrieve(1: i32 uid)
}
